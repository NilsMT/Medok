import { CIS_INFOIMPORTANTESDAO } from "../dao/CIS_INFOIMPORTANTESDAO.mjs"
const CIS_INFOIMPORTANTESController = {
    populate : async () => await CIS_INFOIMPORTANTESDAO.populate(),
    findAll : async () => await CIS_INFOIMPORTANTESDAO.findAll(),
    findByCodeCIS: async() => await CIS_INFOIMPORTANTESDAO.findByCodeCIS()
}

export default CIS_INFOIMPORTANTESController