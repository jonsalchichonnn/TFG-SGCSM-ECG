export default{
    state:{
        routes: [],
    },
    mutations:{
        // Initialize menu routes
        initRoutes(state, data) {
            state.routes = data;
        }
    }
}