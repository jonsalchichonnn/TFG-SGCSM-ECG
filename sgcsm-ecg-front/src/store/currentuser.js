export default {
    state: {
        user: JSON.parse(sessionStorage.getItem("currentUser")),
    },
    mutations: {
        // Initialize menu routes
        initUser(state, user) {
            state.user = user;
        }
    }
}