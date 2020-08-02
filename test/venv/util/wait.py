from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By



class Wait:

    def __init__(self, browser):
        self.browser = browser
        self.wait = WebDriverWait(browser,10)


    def waitElementById(self, idItem):
        return self.wait.until(EC.presence_of_element_located((By.ID, idItem)))


    def waitElementByName(self, nameItem):
        return self.wait.until(EC.presence_of_element_located((By.NAME, nameItem)))

    def waitElementByClassName(self, className):
        return self.wait.until(EC.presence_of_element_located((By.CLASS_NAME, className)))