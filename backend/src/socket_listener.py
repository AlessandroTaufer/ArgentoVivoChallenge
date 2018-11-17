#
#   Author: Alessandro Taufer
#   Email: alexander141220@gmail.com
#   Url: https://github.com/AlessandroTaufer
#
import entities
import socket
import base64
from io import BytesIO
from PIL import Image
import datetime
import database_manager


class Server:
    def __init__(self, parent):
        self.host = "192.168.43.153"
        self.port = 1243
        self.socket = None
        self.parent = parent
        self.listen()

    def listen(self):  # Listen to the given port
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

        print('Socket created')

        try:
            self.socket.bind((self.host, self.port))
        except socket.error as err:
            print('Bind failed. Error Code : '.format(err))
        self.socket.listen(10)
        print("Socket Listening")
        conn, addr = self.socket.accept()
        while True:  # TODO add try catch
            try:
                # conn.send(bytes("Message"+"\r\n"))
                txt = self.recv_end(conn, "\r\n")
                print("client: " + txt)
                if txt.split(" ")[0] == "img":
                    id = txt.split(" ")[1]
                    print("sending image")
                    tosend = self.parent.find_by_id(id)
                    self.send_image(conn, tosend )#"../resources/test.jpg")
                elif txt.split(" ")[0] == "elon":
                    self.send_text(conn, "musk")
                    print("elon time!")
                    self.read_image(conn)
                elif txt.split(" ")[0] == "update":
                    self.parent.find_by_id(txt.split(" ")[1]).rate += int(txt.split(" ")[2])
                elif txt.split(" ")[0] == "view":
                    list =""
                    if txt.split(" ")[1] == "1":
                        list = self.parent.feed()
                    elif txt.split(" ")[1] == "2":
                        tmp_location = txt.split(" ")[2]
                        list = self.parent.locationFeed(tmp_location)
                    else:
                        tmp_author = "".join(txt.split(" ")[2:])
                        print(tmp_author)
                        list = self.parent.authorFeed(tmp_author)
                    self.send_text(conn, list)
            except socket.error as err:
                print('Client disconnected : '.format(err))
                self.socket.listen(10)
                conn, addr = self.socket.accept()
            except KeyboardInterrupt:
                conn.close()

    def decode_image(self,txt):
        txt = txt.split(",")

        id= txt[0]
        location = txt[1].split(" ")
        coordinates = txt[2].split(" ")
        location = self.parent.add_location(entities.Location(location, coordinates))
        date = txt[3].split(" ")
        date = datetime.datetime.strptime(date[0]+"/"+date[1]+"/"+date[2], '%d/%m/%Y')
        author = txt[4]
        author = self.parent.add_creator(entities.Creator(author))
        parent_shot = txt[5]
        descrizione = txt[6]
        tags = txt[6].split(" ")
        im = self.convert_to_Image(txt[7])
        im.show()
        similarity = -200  # TODO calculate that
        ratio = 0
        img = entities.Images(im, id, date,location,coordinates,author,parent_shot,descrizione, tags, similarity, ratio)
        self.parent.imgs.append(img)
        author.shots.append(img)

    def read_text(self, connection, byte=1024):  # Reads text from a socket connection
        txt = ""
        received = 0
        while "\n" not in txt:
            data = connection.recv()
        # received += 1024
            txt += data.decode(encoding='UTF-8')
        txt = txt.replace("\n", "")
        txt = txt.replace("\r", "")
        return txt

    def recv_end(self, the_socket, End):
        total_data = []
        data = ''
        while True:
            data = the_socket.recv(8192)
            if End in data:
                total_data.append(data[:data.find(End)])
                break
            total_data.append(data)
            if len(total_data) > 1:
                # check if end_of_data was split
                last_pair = total_data[-2] + total_data[-1]
                if End in last_pair:
                    total_data[-2] = last_pair[:last_pair.find(End)]
                    total_data.pop()
                    break
        return ''.join(total_data)

    def send_text(self, connection,txt):  # Send a text to the client
        print(txt)
        connection.send(bytes(txt) + "\n")

    def send_image(self, connection, image):  # Send an image to the client
        # url, NomeAutore, idAutore, data (dd/mm/yy), titolo, descrizione, nomeluogo, coordinate(x/y), idluogo, similarita', ratio, idImgRiferimento, NomeImgRiferimento, Tag[tag1/tag2]\n
        tosend = image.to_string()+ ","
        data = image.img  # Image.open(image)
        output = BytesIO()
        data.save(output, format="JPEG")
        hex_data = output.getvalue()
        encoded_string = base64.b64encode(hex_data)
        # print(encoded_string)
        self.send_text(connection, tosend + encoded_string)
        print("done")

    def read_image(self, connection):  # Get an image from the client
        data = self.recv_end(connection, "\r\n")
        self.decode_image(data)

    @staticmethod
    def convert_to_Image(data):
        im = Image.open(BytesIO(base64.b64decode(data)))
        im.show()
        return im


if __name__ == "__main__":
    Server()
    # HOST = "192.168.43.153"
    # PORT = 1234
    # s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # print('Socket created')
    #
    # try:
    #     s.bind((HOST, PORT))
    # except socket.error as err:
    #     print('Bind failed. Error Code : ' .format(err))
    # s.listen(10)
    # print("Socket Listening")
    # conn, addr = s.accept()
    # while(True):
    #     # conn.send(bytes("Message"+"\r\n"))
    #     # print("Message sent")
    #     data = conn.recv(1024)
    #     print("Stop")
    #     print(data.decode(encoding='UTF-8'))
    #
