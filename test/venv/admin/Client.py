from selenium.webdriver.support.ui import Select

class Client:

    def __init__(self, Shaman, wait):
        self.wait = wait;
        self.driver = Shaman.getDriver()

    def insertField(self, nameInput, text):
        self.driver.find_element_by_name(nameInput).send_keys(text)

    def clickByid(self, id):
        self.driver.find_element_by_id(id).click()

    def clickByName(self, name):
        self.driver.find_element_by_name(name).click()

    def clickByClassName(self, className):
        self.driver.find_element_by_class_name(className).click()

    def selectItemClick(self, index, father, tag ):
        # father = "radio-group"
        # tag = label
        # idenx = 0
        parentElement = self.driver.find_element_by_class_name(father)
        elementList = parentElement.find_elements_by_tag_name(tag)
        elementList[index].click()


    def selectDropItem(self, nameSelector):
        element = self.driver.find_element_by_name(nameSelector)
        dpr = Select(element)
        dpr.select_by_index(0)


    def addClient(self):
        self.wait.waitElementByName("Clientes")
        self.clickByName("Clientes")

        self.wait.waitElementByClassName("btn-add")
        self.clickByClassName("btn-add")
        self.insertField( "nome", "Pedro")
        self.insertField( "cpf/cnpj", "11122233343")
        self.insertField( "email", "teste@gmail.com")
        self.insertField( "senha", "123")
        self.insertField( "telefone", "35999343563")

        self.selectDropItem("cidade")
        self.insertField( "logradouro", "Av. Primavera 99")
        self.insertField( "cep", "37490000")

        self.selectItemClick(0,"bv-no-focus-ring", "label")
        save = self.selectItemClick(1, "modal-footer", "button")