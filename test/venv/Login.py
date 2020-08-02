import Website


class Login:
    
    def __init__(self, Shaman, wait):
        self.Shaman = Shaman
        self.driver = Shaman.getDriver()
        self.wait = wait

    def clickButtonById(self, idButton):
        self.driver.find_element_by_id(idButton).click()

    def clickButtonByClassName(self, className):
        self.driver.find_element_by_class_name(className).click()
    
    def inputField(self, idInputUser, username):

        field = self.driver.find_element_by_id(idInputUser).send_keys(username)

    
    def loginUser(self, username, password):
        self.Shaman.navigate("")
        self.clickButtonById("btn-login")
        self.inputField("input-login", username)
        self.inputField("input-password", password)
        self.clickButtonByClassName("entrar")
    
    
    def loginAdmin(self, username, password):
        self.Shaman.navigate("login")
        self.inputField("input-login", username)
        self.inputField("input-password", password)
        self.clickButtonById("btn-login")

