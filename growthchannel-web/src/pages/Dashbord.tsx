import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { Bar, BarChart, CartesianGrid, Legend, Line, LineChart, Pie, PieChart, ResponsiveContainer, Tooltip, XAxis, YAxis } from 'recharts';
import Cookies from 'js-cookie'
import { getAuthUrl, getUserInfo } from '../request/AuthRequest';
import { PROMPT_CONSENT, REDIRECT_ANALYTICS, SCOPE_ANALYTICS } from '../constants/authConstant';
import { lineData, barData, pieData } from '../constants/mockData';

const Home: React.FC = () => {

    const token = Cookies.get('token')
    const localToken = localStorage.getItem('token')

    if (token == null && localToken == null) {
        window.location.href = "/"
    }
    if (token !== null && token !== localToken) {
        localStorage.setItem("token", Cookies.get("token") as string);
    }

    const [open, setOpen] = useState<boolean>(true);

    const [OLineData, setOLineData] = useState<any[]>([])

    const [OBarData, setOBarData] = useState<any[]>([])

    const [OPieData, setOPieData] = useState<any[]>([])

    useEffect(() => {
        getUserInfo(data => {
            setOpen(data.isConnectGoogleAnalytics)
            if (data.isConnectGoogleAnalytics) {
                setOLineData(lineData)
                setOPieData(pieData)
                setOBarData(barData)
            }
        })
    }, [])

    const handleClickAgree = () => {
        getAuthUrl({
            scope: SCOPE_ANALYTICS,
            redirectUrl: REDIRECT_ANALYTICS,
            prompt: PROMPT_CONSENT
        }, data => {
            window.location.href = data
        })
    }

    const handleClose = () => {
        setOpen(false);
    }

    return (
        <div className="w-screen h-screen flex justify-center">
            <div className={" w-1/3 h-1/3"}>
                <MyLineChart data={OLineData} />
            </div>
            <div className={"w-1/3 h-1/3"}>
                <MyAreaChart data={OBarData} />
            </div>
            <div className={"w-1/3 h-1/3"}>
                <MyPieCharts data={OPieData} />
            </div>

            <Dialog
                open={!open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">
                    {"Connect Google Analytics"}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Do you want to connect Google Analytics?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Disagree</Button>
                    <Button onClick={handleClickAgree} autoFocus>
                        Agree
                    </Button>
                </DialogActions>
            </Dialog>
        </div>)
}

const MyPieCharts = (props: any) => {
    const { data } = props

    return <ResponsiveContainer width="100%" height="100%">
        <PieChart width={400} height={400}>
            <Pie data={data} dataKey="value" cx="50%" cy="50%" innerRadius={70} outerRadius={90} fill="#82ca9d" label />
        </PieChart>
    </ResponsiveContainer>
}


const MyAreaChart = (props: any) => {
    const { data } = props
    return <ResponsiveContainer width="100%" height="100%">
        <BarChart
            width={500}
            height={300}
            data={data}
            margin={{
                top: 5,
                right: 30,
                left: 20,
                bottom: 5,
            }}
        >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Bar dataKey="pv" fill="#8884d8" />
            <Bar dataKey="uv" fill="#82ca9d" />
        </BarChart>
    </ResponsiveContainer>
}

const MyLineChart = (props: any) => {
    const { data } = props
    return <ResponsiveContainer width="100%" height="100%">
        <LineChart
            width={500}
            height={300}
            data={data}
            margin={{
                top: 5,
                right: 30,
                left: 20,
                bottom: 5,
            }}
        >
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Line type="monotone" dataKey="pv" stroke="#8884d8" activeDot={{ r: 8 }} />
            <Line type="monotone" dataKey="uv" stroke="#82ca9d" />
        </LineChart>
    </ResponsiveContainer>
}

export default Home;

