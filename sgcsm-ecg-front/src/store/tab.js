export default{
    state:{
        isCollapse: false
    },
    mutations:{
        changeCollapse(state){
            state.isCollapse = !state.isCollapse
        }
    }
}