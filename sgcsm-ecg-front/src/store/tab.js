export default{
    state:{
        isCollapse: false
    },
    mutations:{
        changeCollapse(state){
            // console.log("state: ", state.isCollpase)
            state.isCollapse = !state.isCollapse
        }
    }
}