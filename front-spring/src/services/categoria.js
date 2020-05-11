import Api from './config'

const getAll = () => {
    return Api().get('categoria');
}

const post = (data)=>{
    return Api().post('/categoria',data)
}

const del = (id) => {
    return Api().delete(`/categoria/${id}`)
}

const put = (id, data) => {
    return Api().put(`/categoia/${id}`,data)
}

export default {
    getAll,
    post,
    del,
    put
}