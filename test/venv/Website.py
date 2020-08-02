
class WebSite:
    def __init__(self, driver):
        self.driver = driver
        self.url = 'http://localhost:8080/'

    def getDriver(self):
        return self.driver

    def navigate(self,extension):
        self.driver.get(self.url+extension)







