import Api from './config'


const getPage = (ids) => {
    

    return Api().get(`/produtos?categorias=${ids}`)
}

const getAll = () => {
    return Api().get('/produtos')
}

const post = (data) => {
    return Api().post('/produtos', data);
}

const del = (id) => {
    let token = localStorage.getItem('token')

    const config = {
        headers: {
            Authorization: "Bearer "+token
        }
    }
    return Api().delete(`/produtos/${id}`, config)
}

const put = (data) => {
    return Api().put(`/produtos/${data.id}`, data)
}
const putCategoria = (id, data) => {
    
    return Api().put(`/produtos/categoria/${id}`, data)
}

export default {
    getPage,
    post,
    del,
    put,
    getAll,
    putCategoria,

}