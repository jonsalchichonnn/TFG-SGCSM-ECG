// download utils for exporting data
// via axios we make requests to our backend
import axios from "axios";

// set a default response type (arraybuffer as Excel should be returned) for axios
const service = axios.create({
    responseType: 'arraybuffer'
})

// response interceptor
service.interceptors.response.use(resp => {
    const headers = resp.headers
    let reg = RegExp(/application\/json/) // to check if it's json or stream
    // if match => regular request
    if (headers['content-type'].match(reg)) {
        resp.data = uintToString(resp.data) // get JSON string object
    } else {
        let fileDownload = require('js-file-download') // download dependency
        let fileName = headers['content-disposition'].split(';')[1].split('filename=')[1]
        let contentType = headers['content-type']
        fileName = decodeURIComponent(fileName) // convert formats to prevent Mojibake
        fileDownload(resp.data, fileName, contentType) // download file via dependency

    }
}, error => {
    console.log(error)
})

// convert json (encode, decode ）
function uintToString(uintArray) {
    let encodedString = String.fromCharCode.apply(null, new Uint8Array(uintArray))
    let decodedString = decodeURIComponent(escape(encodedString))
    return JSON.parse(decodedString) // convert to object
}

// download Request
export const downloadRequest = (url, params) => {
    return service({
        method: 'get',
        url: `http://localhost:8090${url}`,
        data: params
    })
}

export default service;