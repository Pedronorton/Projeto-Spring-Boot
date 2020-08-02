from selenium import webdriver
from user.Shop import Shop
from admin.Category import Category 
from admin.Client import Client
from admin.Product import Product
from Login import *
from util.wait import *
from Website import *



import time
browser = webdriver.Chrome(executable_path=r'./chromedriver.exe')
Shaman = WebSite(browser)
def main():

    wait = Wait(browser)
    shop = Shop(Shaman, wait)
    category = Category(Shaman, wait)
    client = Client(Shaman, wait)
    product = Product(Shaman, wait)
    login = Login(Shaman, wait)

    # login.loginAdmin("admin@gmail.com", "123" )
    login.loginUser("user@gmail.com","123")
    shop.shopByCategoria("informatica")
    # shop.shop()#Em alguns sistemas é necessário estar com antivirus desativado pois ele envia um email automático de confirmação
    # category.addCategory()
    # category.editCategory()
    # client.addClient()
    # product.addProduct()
    # product.editProduct()
    # product.deleteProduct()

if __name__ == '__main__':
    main()
