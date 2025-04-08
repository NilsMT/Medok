import { HAS_LIENSPAGECTDAO } from "../dao/HAS_LIENSPAGECTDAO.mjs"

const HAS_LIENPAGESCTController = {
    populate : async () => await HAS_LIENSPAGECTDAO.populate(),
    findAll : async (page,size) => await HAS_LIENSPAGECTDAO.findAll(page,size)
}

export default HAS_LIENPAGESCTController