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

export default {
    getAll,
    post,
    del
}