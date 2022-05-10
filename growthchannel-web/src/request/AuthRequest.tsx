import { get, getWithAuth } from "../utils/httputil"
import {HOST} from '../constants/systemConstant'

interface GetAuthUrlRequest {
    scope: string
    redirectUrl: string
    prompt: string
}


export const getAuthUrl = (params: GetAuthUrlRequest, callback: (data: any) =>void) => {
    get(`${HOST}/oauth/Google/url`, params, callback)
}


export const getUserInfo = (callback: (data: any) => void) => {
    getWithAuth(`${HOST}/userInfo`, {}, callback)
}

