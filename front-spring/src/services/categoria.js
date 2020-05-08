import Api from './config'

const getAll = () => {
    return Api().get('categoria');
}

export default {
    getAll
}