import { CIS_COMPODAO } from "../dao/CIS_COMPODAO.mjs"

const CIS_COMPOController = {
    populate : async () => await CIS_COMPODAO.populate(),
    findAll : async (page,size) => await CIS_COMPODAO.findAll(page,size),
    findByCodeCIS: async(codecis_API) => await CIS_COMPODAO.findByCodeCIS(codecis_API)
}

export default CIS_COMPOController