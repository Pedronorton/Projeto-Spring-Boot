

class Category:

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
        # father = "modal-footer"
        # tag = button
        # idenx = 1
        parentElement = self.driver.find_element_by_class_name(father)
        elementList = parentElement.find_elements_by_tag_name(tag)
        elementList[index].click()


    def addCategory(self):
        self.wait.waitElementByName("Categorias")
        self.clickByName("Categorias")

        self.wait.waitElementByClassName("btn-add")
        self.clickByClassName("btn-add")
        self.insertField("categoria", "Teste automatizado")
        save = self.selectItemClick(1, "modal-footer", "button")

    def editCategory(self):
        self.wait.waitElementByName("Categorias")
        self.clickByName("Categorias")
        self.wait.waitElementByName("editar")
        self.clickByName("editar")
        self.clearField("nome")
        self.insertField("nome", "Produto editado")
        self.clickByName("salvar")


