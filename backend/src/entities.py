#
#   Author: Alessandro Taufer
#   Email: alexander141220@gmail.com
#   Url: https://github.com/AlessandroTaufer
#
import datetime
import base64
from io import BytesIO
from PIL import Image


class Images:
    def __init__(self, img, id, date, location, author, parent, description, tag, similarity, like_ratio):
        self.img = img  # Img
        self.id = id  # Image identifier
        self.date = date  # Shot date
        self.location = location   # Shot location
        self.author = author  # Shot author
        self.parent = parent  # Parent shot referred to
        self.description = description  # Shot description
        # self.title = ""  # Shot title
        self.tag = tag  # Shot tag
        self.similarity = similarity  # Percentage of similarity with the parent
        self.like_ratio = like_ratio  # Ratio like/dislike

        self.author_id = 1
        self.coordinates = [10, 12]
        self.location_id = 123

        self.parent_id = ""
        self.parent_title = ""

    def to_string(self):  # Convert the image into string
        tag = "".join([str(self.tag[i]) for i in range(len(self.tag))])
        date = self.date.strftime('%d/%m/%Y')
        if (self.parent == None):
            parentid = ""
        else:
            parentid = self.parent.id
        # NomeAutore, idAutore, data (dd/mm/yy), descrizione,nomeluogo, coordinate(x/y), idluogo, similarita', ratio, idImgRiferimento, NomeImgRiferimento, Tag[tag1/tag2]\n
        txt = str(self.author.name) + "," + str(self.author.id) + "," + str(date) + "," + str(self.description) + "," + str(self.location.name) + "," + (self.location.coordinates if self.location != None else "69/69") + "," \
            + str(self.location.id if self.location != None else "") + "," + str(self.similarity) + "," + str(self.like_ratio) + "," + str(parentid) + "," + "," + tag
        return txt


class Location:
    def __init__(self, name, coordinates):
        self.id = 0
        self.name = name  # Location name
        self.coordinates = coordinates  # Shot coordinates
        self.time_line = []  # Time line shots of the location


class Creator:
    def __init__(self, name, id):
        self.id = id  # Creator identifier
        self.name = name  # Creator Name
        self.shots = []  # List of shots
        self.profile_pic = None  # Profile picture
        self.follower = []  # List of creators that are current followers


if __name__ == "__main__":
    # with open("../resources/test.jpg", "rb") as image_file:
    #     encoded_string = base64.b64encode(image_file.read())
    #     print(encoded_string)
    data = Image.open("../resources/test.jpg")
    output = BytesIO()
    data.save(output, format="JPEG")
    hex_data = output.getvalue()
    encoded_string = base64.b64encode(hex_data)
    print(encoded_string)

