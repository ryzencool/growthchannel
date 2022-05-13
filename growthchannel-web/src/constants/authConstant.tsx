import { HOST } from "./systemConstant"

const SCOPE_PROFILE: string = "https://www.googleapis.com/auth/userinfo.profile"

const SCOPE_MAIL: string = "https://www.googleapis.com/auth/userinfo.email"

const SCOPE_ANALYTICS: string = "https://www.googleapis.com/auth/analytics"

const REDIRECT_LOGIN: string = `${HOST}/oauth/Google/login`

const REDIRECT_ANALYTICS: string = `${HOST}/analytics/connect`

const PROMPT_SELECT_ACCOUNT: string = "select_account"

const PROMPT_CONSENT: string = "consent"

const TOKEN_STR: string = "token"

export {
    SCOPE_ANALYTICS,
    SCOPE_MAIL,
    SCOPE_PROFILE,
    REDIRECT_LOGIN,
    REDIRECT_ANALYTICS,
    PROMPT_SELECT_ACCOUNT,
    PROMPT_CONSENT,
    TOKEN_STR
}

