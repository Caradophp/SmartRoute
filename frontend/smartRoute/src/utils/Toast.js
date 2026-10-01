
import { toast } from "vue3-toastify";

export default class Toast {

    static success(message) {
        toast(message, {'type': 'error'})
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
}