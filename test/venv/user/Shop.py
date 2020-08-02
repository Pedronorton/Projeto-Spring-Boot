import time
from selenium.webdriver.support.ui import Select


class Shop:

    def __init__(self, Shaman, wait):
        self.wait = wait;
        self.driver = Shaman.getDriver()

    def getBoxes(self, boxName):
        time.sleep(2)
        return self.driver.find_elements_by_class_name(boxName)

    def getAllProducts(self, boxName):
        boxes = self.getBoxes(boxName)
        self.wait.waitElementById('link-cart')
        for box in boxes:
            box.find_element_by_id('link-cart').click()

    def openModal(self, idButton):
        self.driver.find_element_by_id(idButton).click()

    def openCart(self, idButton):
        self.driver.find_element_by_id(idButton).click()

    def openAdress(self, idSelector):
        time.sleep(1)
        element = self.driver.find_element_by_id(idSelector)
        dpr = Select(element)
        dpr.select_by_index(0)

    def finalizePurshase(self, idButton):
        element = self.driver.find_element_by_id(idButton).click()

    def clickCategoria(self, idButton):
        element = self.driver.find_element_by_id(idButton).click()

    def selectCategoria(self, categoria):
        element = self.driver.find_element_by_name(categoria).click()

    def shopByCategoria(self, categoria):

        self.wait.waitElementByName(categoria)
        self.selectCategoria(categoria)
        self.shop()

    def shop(self):
        self.wait.waitElementById("link-cart") #espera ter os produtos na DOM
        self.getAllProducts( "product-card")
        self.wait.waitElementById("button-modal-cart")
        self.openModal("button-modal-cart")
        time.sleep(1)
        self.openCart("button-cart")

        self.wait.waitElementById("adress")
        self.openAdress( "adress" )
        self.finalizePurshase("fechar-pedido")

