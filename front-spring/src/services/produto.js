import Api from './config'


const getPage = () => {
    return Api().get('/produtos')
}

const post = (data) => {
    return Api().post('/produtos', data);
}


export default {
    getPage,
    post,

}