import Api from './config'


const getPage = (ids) => {
    

    return Api().get(`/produtos?categorias=${ids}`)
}

const getAll = () => {
    return Api().get('/produtos')
}

const post = (data) => {
    let formData = new FormData();
    formData.append('nome', data.nome);
    formData.append('preco', data.preco);
    formData.append('file', data.imageUrl);
    
    return Api().post('/produtos', formData);
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
    let formData = new FormData();
    formData.append('nome', data.nome);
    formData.append('preco', data.preco);
    formData.append('file', data.imageUrl);
    const config = {
        headers: {
            "content-Type": "multipart/form-data",
        }
    }

    return Api().put(`/produtos/${data.id}`, formData, config)
}
const putCategoria = (id, data) => {
    
    return Api().put(`/produtos/categoria/${id}`, data)
}
const getCategoria = (id) => {
    
    return Api().get(`/produtos/categoria/${id}`)
}

export default {
    getPage,
    post,
    del,
    put,
    getAll,
    putCategoria,
    getCategoria

}