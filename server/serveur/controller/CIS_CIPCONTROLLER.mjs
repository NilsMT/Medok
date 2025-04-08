import { CIS_CIPDAO } from "../dao/CIS_CIPDAO.mjs"

const CIS_CIPController = {
    populate : async () => await CIS_CIPDAO.populate(),
    findAll : async (page,size) => await CIS_CIPDAO.findAll(page,size),
    findByCodeCIS: async(codecis_API) => await CIS_CIPDAO.findByCodeCIS(codecis_API)
}

export default CIS_CIPController