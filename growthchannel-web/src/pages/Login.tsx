import Button from '@mui/material/Button'
import React from 'react'
import { PROMPT_SELECT_ACCOUNT, REDIRECT_LOGIN, SCOPE_PROFILE } from '../constants/authConstant'
import { getAuthUrl } from '../request/AuthRequest'

const Login: React.FC = () => {

    const handleClickGoogleLogin = () => {
        getAuthUrl({
            scope: SCOPE_PROFILE,
            redirectUrl: REDIRECT_LOGIN,
            prompt: PROMPT_SELECT_ACCOUNT
        }, (data: any) => {
            window.location.href = data
        })
    }

    return (
        <div className={"flex justify-center items-center h-screen"}>
            <div className={"flex flex-col rounded-3xl  border-solid border-2 border-gray-200 p-5 gap-3"}>
                <div className={"font-bold text-lg m-auto"}>SignIn</div>
                <div className={"h-1 w-6 bg-orange-400 m-auto rounded-md"}></div>
                <Button onClick={handleClickGoogleLogin}>Login With Google</Button>
            </div>
        </div>

    )
}


export default Login;