import axios from 'axios'
import { TOKEN_STR } from '../constants/authConstant'

export const get = (url: string, params: any, callback: (data: any) => void) => {
    axios.get(url, {
        params: params
    }).then(res => {
        if (res.data.code !== "000000") {
            // todo: add global notification
        } else {
            callback(res.data.data)
        }
    }).catch(err => {
        // todo: add global notification 
    })
}

export const getWithAuth = (url: string, params: any, callback: (data: any) => void) => {
    const jwt: string | null = localStorage.getItem(TOKEN_STR)
    if (jwt === null) {
        // redirct to login page
    }
    axios.get(url, {
        headers: {
            'Authorization': jwt as string
        },
        params: params
    }).then(res => {
        if (res.data.code !== "000000") {
            // todo: add global notification
        } else {
            callback(res.data.data)
        }
    }).catch(err => {
        // todo: add global notification 
    })

}

