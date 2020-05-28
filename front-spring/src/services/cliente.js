import Api from './config'

const getAll = () =>{
    return Api().get('clientes')
}

const getOne = (id) => {
    return Api().get(`/clientes/${id}`)
}

const post = (data) => {
    return Api().post('/clientes', data)
}

const del = (id) => {
    return Api().delete(`/clientes/${id}`)
}

const put = (id,data) => {
    return Api().put(`/clientes/${id}`,data)
}

const getUserAutenticated = () => {
    let token = localStorage.getItem('token')
    const config = {
        headers: {
            Authorization: "Bearer "+token
        }
    }
    return Api().get('clientes/autenticated', config)
}

const postPicture = (data) => {
    let token = localStorage.getItem('token')
    let formData = new FormData();
    formData.append('file', data)

    const config = {
        headers: {
            "content-Type": "multipart/form-data",
            Authorization: "Bearer "+token
        }
    }

    return Api().post('/clientes/picture', formData, config)
}
export default {
    getAll,
    getOne,
    post,
    del,
    put,
    postPicture,
    getUserAutenticated
}