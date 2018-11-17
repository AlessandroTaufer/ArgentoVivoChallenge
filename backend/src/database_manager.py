#
#   Author: Alessandro Taufer
#   Email: alexander141220@gmail.com
#   Url: https://github.com/AlessandroTaufer
#
import entities
import mysql.connector
import urllib2
import json
import datetime
import cStringIO
from PIL import Image
import socket_listener
import csv
from io import BytesIO
import random


class DatabaseManager:
    def __init__(self, username="root", pw=""):
        self.host = "localhost"  # Database host
        self.user = username  # Database username
        self.pw = pw  # Database pw
        self.database = "argento"
        self.mydb = mysql.connector.connect(host=self.host, user=self.user, passwd=self.pw, database=self.database)


class ArgentoInterface:
    def __init__(self, endpoint):
        self.endpoint = endpoint
        self.imgs = []
        self.creators = []
        self.locations = []

    def backup(self):  # Backups the imgs
        import pickle
        filename = '../resources/dataset'
        outfile = open(filename, 'wb')
        pickle.dump(self.imgs, outfile)
        outfile.close()

    def add_location(self, location):
        for i in self.locations:
            if i.name == location.name:
                return i
        self.locations.append(location)
        return location

    def add_creator(self, author):
        for i in self.creators:
            if i.name == author.name:
                return i
        print("appended creator")
        self.creators.append(author)
        return author

    def sort(self):
        done = False
        while not done:
            done = True
            for i in range(len(imgs)-1):
                if imgs[i] < imgs[i+1]:
                    tmp  = imgs[i]
                    imgs[i] = imgs[i+1]
                    imgs[1+i] = tmp
                    done = False

    def feed(self):  # List sorted by like
        self.sort()
        rtn = ""
        for i in self.imgs:
            rtn += i.id + ","
        return rtn

    def locationFeed(self, location):  # List sorted by location
        tmplist = []
        for e in self.imgs:
            if e.location.name == location:
                tmplist.append(e)
        done = False
        while not done:
            done = True
            for i in range(len(tmplist) - 1):
                if tmplist[i] < tmplist[i + 1]:
                    tmp = tmplist[i]
                    tmplist[i] = tmplist[i + 1]
                    tmplist[1 + i] = tmp
                    done = False

        rtn = ""
        for i in tmplist:
            rtn += i.id+","
        return rtn

    def authorFeed(self, name):  # List sorted by location
        creator = None
        print("creators" + str(self.creators))
        for e in self.creators:
            if e.name.replace(" ", "").lower() == (name.replace(" ", "")).lower():
                creator = e
        if creator is None:
            print ("Creator not found")
            return -1
        tmplist = creator.shots
        tmplist.reverse()
        rtn = ""
        for i in tmplist:
            rtn += i.id+","
        return rtn


    def load_imgs(self):
        import pickle
        filename = '../resources/dataset'
        infile = open(filename, 'rb')
        self.imgs = pickle.load(infile)
        infile.close()

    def read_content(self, query):  # Read the content of the http page
        url = self.endpoint + query
        print(url)
        content = urllib2.urlopen(url).read()
        return content

    def clean_content(self, content):  # Cleans the content
        datastore = json.loads(content)
        # print(datastore)
        for i in range(len(datastore["response"]["docs"])):
            try:

                id = datastore["response"]["docs"][i]["IN"]

                coordinates = datastore["response"]["docs"][i]["CP_geo"][0].replace(",", "/")
                print(coordinates)
                date = self.convert_date(datastore["response"]["docs"][i]["DS"][0])
                location_name= datastore["response"]["docs"][i]["CP_it"][0].replace(",","/")
                location = self.add_location(entities.Location(location_name, coordinates))
                author_name = datastore["response"]["docs"][i]["VV_it"][0].replace(",", "")
                author = self.add_creator(entities.Creator(author_name, random.randint(0, 65536)))
                parent = None
                description = datastore["response"]["docs"][i]["BE_it"][0].replace(",",". ")
                tag = datastore["response"]["docs"][i]["ip_it"][0]
                similarity = 100
                like_ratio = 0
                url = datastore["response"]["docs"][i]["B1p_url"][0]+"&size=l"
                img = self.saveImage(url)
                e = entities.Images(img, id, date, location, author, parent, description, tag, similarity, like_ratio)
                self.imgs.append(e)
                author.shots.append(e)
            except KeyError as err:
                print(err)
                pass
            except:
                pass
        return self.imgs
        # rows = content.split("\n")
        # columns = [rows[i].split(",") for i in range(len(rows))]
        # print (columns[0])
        # print(columns[1])
        # pos = self.get_column("B1p_url", columns[0])
        # print(columns[1][pos])
        # # print(content)

    def find_by_id(self, id):
        for e in self.imgs:
            if e.id == id:
                return e

    @staticmethod
    def convert_date(date):
        date = str(date).split(".")
        year = str(date[0])
        month = str(date[1][0] + date[1][1])
        day = str(date[1][2] + date[1][3])
        return datetime.datetime.strptime(month+"/"+day+"/"+year, '%m/%d/%Y')

    def get_column(self, title, columns):  # Find the corresponding column\n",
        for i in range(len(columns)):
            if columns[i] == title:
                return i
        return -1

    def saveImage(self, url):  # Download the image from the http page
        # f = open(name, 'wb')
        # f.write(urllib2.urlopen(url).read())
        # f.close()

        imgdata = urllib2.urlopen(url).read()
        img = Image.open(cStringIO.StringIO(imgdata))
        # img.show()
        return img


if __name__ == "__main__":
    a = ArgentoInterface("http://dati.retecivica.bz.it/services/kksSearch/collect/lichtbild/")
    tmp = a.read_content("select?q=*:*&sort=creation%20desc&rows=20&fl=IN&fl=DS&fl=CP_it&fl=VV_it&fl=CP_geo&fl=BE_it&fl=ip_it&fl=B1p&fl=B1p_url&wt=json")
    # tmp = a.read_content("select?q=CP_it:Bolzano&sort=creation%20desc&rows=10&fl=IN&fl=DS&fl=CP_it&fl=VV_it&fl=CP_geo&fl=BE_it&fl=ip_it&fl=B1p&fl=B1p_url&wt=json")
    imgs = a.clean_content(tmp)
    # tmp = a.load_imgs()
    # a.backup()
    socket_listener.Server(a)
    # ArgentoInterface().saveImage("../resources/test.jpg", "https://cert.provinz.bz.it/services/kksSearch/image?file=LAV039-01224.jpg&mus=LAV")
