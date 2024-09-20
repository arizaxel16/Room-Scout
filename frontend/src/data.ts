import { FaUsersBetweenLines } from "react-icons/fa6";
import { LuClipboardList } from "react-icons/lu";
import { FaCoins } from "react-icons/fa";
import { MdAspectRatio } from "react-icons/md";

export const topDealUsers = [
    {
        id: 1,
        img: "https://images.pexels.com/photos/8405873/pexels-photo-8405873.jpeg?auto=compress&cs=tinysrgb&w=1600&lazy=load",
        username: "Elva McDonald",
        email: "elva@gmail.com",
        amount: "3.668"
    },
    {
        id: 2,
        img: "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=1600",
        username: "Linnie Nelson",
        email: "linnie@gmail.com",
        amount: "3.256"
    },
    {
        id: 3,
        img: "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=1600",
        username: "Brent Reeves",
        email: "brent@gmail.com",
        amount: "2.998"
    },
    {
        id: 4,
        img: "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=1600",
        username: "Brent Reeves",
        email: "brent@gmail.com",
        amount: "2.998"
    },
    {
        id: 5,
        img: "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=1600",
        username: "Brent Reeves",
        email: "brent@gmail.com",
        amount: "2.998"
    },
    {
        id: 6,
        img: "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=1600",
        username: "Brent Reeves",
        email: "brent@gmail.com",
        amount: "2.998"
    },
    {
        id: 7,
        img: "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=1600",
        username: "Brent Reeves",
        email: "brent@gmail.com",
        amount: "2.998"
    },
]
export const chartBoxUser = {
    color: "#8884d8",
    icon: FaUsersBetweenLines,
    title: "Total Users",
    number: "11.238",
    dataKey: "users",
    percentage: 45,
    chartData: [
        {name: "Sun", users: 400},
        {name: "Mon", users: 600},
        {name: "Tue", users: 500},
        {name: "Wed", users: 700},
        {name: "Thu", users: 400},
        {name: "Fri", users: 500},
        {name: "Sat", users: 400},
    ]
}
export const chartBookings = {
    color: "#FF8042",
    icon: LuClipboardList,
    title: "Total Bookings",
    number: "258",
    dataKey: "bookings",
    percentage: 35,
    chartData: [
        {name: "Sun", bookings: 400},
        {name: "Mon", bookings: 600},
        {name: "Tue", bookings: 500},
        {name: "Wed", bookings: 700},
        {name: "Thu", bookings: 400},
        {name: "Fri", bookings: 500},
        {name: "Sat", bookings: 400},
    ]
}
export const chartRevenue = {
    color: "#2880d9",
    icon: FaCoins,
    title: "Total Revenues",
    number: "$56,432",
    dataKey: "revenue",
    percentage: 48,
    chartData: [
        {name: "Sun", revenue: 400},
        {name: "Mon", revenue: 600},
        {name: "Tue", revenue: 500},
        {name: "Wed", revenue: 700},
        {name: "Thu", revenue: 400},
        {name: "Fri", revenue: 500},
        {name: "Sat", revenue: 400},
    ]
}
export const chartRatio = {
    color: "#a23737",
    icon: MdAspectRatio,
    title: "Total Ratio",
    number: "2.6",
    dataKey: "ratio",
    percentage: -5,
    chartData: [
        {name: "Sun", ratio: 400},
        {name: "Mon", ratio: 600},
        {name: "Tue", ratio: 500},
        {name: "Wed", ratio: 700},
        {name: "Thu", ratio: 400},
        {name: "Fri", ratio: 500},
        {name: "Sat", ratio: 400},
    ]
}
export const chartBoxVisit = {
    title: "Total Visit",
    color: "#8884d8",
    dataKey: "visit",
    chartData: [
        {name: "Sun", visit: 400},
        {name: "Mon", visit: 600},
        {name: "Tue", visit: 500},
        {name: "Wed", visit: 700},
        {name: "Thu", visit: 400},
        {name: "Fri", visit: 500},
        {name: "Sat", visit: 400},
    ]
}
export const chartBoxRevenue = {
    title: "Profit Earned",
    color: "#82ca9d",
    dataKey: "revenue",
    chartData: [
        {name: "Sun", revenue: 400},
        {name: "Mon", revenue: 600},
        {name: "Tue", revenue: 500},
        {name: "Wed", revenue: 700},
        {name: "Thu", revenue: 400},
        {name: "Fri", revenue: 500},
        {name: "Sat", revenue: 400},
    ]
}
