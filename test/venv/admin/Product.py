from selenium.webdriver.support.ui import Select
import time

class Product:

    def __init__(self, Shaman, wait):
        self.wait = wait;
        self.driver = Shaman.getDriver()


    def insertField(self, nameInput, text):
        self.driver.find_element_by_name(nameInput).send_keys(text)

    def clearField(self, nameInput):
        self.driver.find_element_by_name(nameInput).clear()


    def clickByid(self, id):
        self.driver.find_element_by_id(id).click()


    def clickByName(self, name):
        self.driver.find_element_by_name(name).click()


    def clickByClassName(self, className):
        self.driver.find_element_by_class_name(className).click()


    def selectItemClick(self, index, father, tag):
        # father = "radio-group"
        # tag = label
        # idenx = 0
        parentElement = self.driver.find_element_by_class_name(father)
        elementList = parentElement.find_elements_by_tag_name(tag)
        time.sleep(1)
        elementList[index].click()

    def selectCategory(self, idSelector):

        element = self.driver.find_element_by_id(idSelector)
        dpr = Select(element)
        dpr.select_by_index(1)
        dpr.select_by_index(2)

    def selectDropItem(self, nameSelector):
        element = self.driver.find_element_by_name(nameSelector)
        dpr = Select(element)
        dpr.select_by_index(0)

    def fileInput(self, nameInput):
        file_input = self.driver.find_element_by_name(nameInput)
        # é necessario alterar o caminho para a imagem fazer upload
        file_input.send_keys("C:/Users/pedro/Desktop/celular.png")


    def editProduct(self):
        self.wait.waitElementByName("Produtos")
        self.clickByName("Produtos")

        self.wait.waitElementByName("editar")
        self.clickByName("editar")
        self.clearField("preco")
        self.insertField( "preco", "100")
        self.selectCategory("tags")
        self.fileInput("file")
        self.clickByName("salvar")

    def deleteProduct(self):
        self.wait.waitElementByName("Produtos")
        self.clickByName("Produtos")

        self.wait.waitElementByName("deletar")
        self.clickByName("deletar")

        self.selectItemClick(1, "modal-footer","button")


    def addProduct(self):
        self.wait.waitElementByName("Produtos")
        self.clickByName("Produtos")
        self.wait.waitElementByClassName("btn-add")
        self.clickByClassName("btn-add")
        self.insertField("nome", "produto teste")
        self.insertField("preco", "50")
        self.selectDropItem("tags")
        self.selectCategory("tags") 

        self.fileInput("file")
        self.selectItemClick(1,"modal-footer","button")
