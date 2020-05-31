import Api from './config'


const getPage = (ids) => {
    

    return Api().get(`/produtos?categorias=${ids}`)
}

const post = (data) => {
    return Api().post('/produtos', data);
}

const del = (id) => {
    return Api().delete(`/produtos/${id}`)
}

const put = (id,data) => {
    return Api().put(`/produtos/${id}`, data)
}

export default {
    getPage,
    post,
    del,
    put,

}