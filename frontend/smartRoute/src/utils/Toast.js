
import { toast } from "vue3-toastify";

export default class Toast {

    static success(message) {
        toast(message, {'type': 'success'})
    }

    static info(message) {
        toast(message, {'type': 'info'})
    }

    static warning(message) {
        toast(message, {'type': 'warning'})
    }
    
    static danger(message) {
        toast(message, {'type': 'error'})
    }

    static error(erro) {
        if (!erro.response) {
            this.danger(erro)
            return;
        }
        const data = erro.response.data;
        if (data.info) {
            this.info(data.info)
        } else if (data.aviso) {
            this.warning(data.aviso)
        } else {
            toast(data.error, {'type': 'error'})   
        }
    }
}