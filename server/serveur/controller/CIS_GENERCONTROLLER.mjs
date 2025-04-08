import { CIS_GENERDAO } from "../dao/CIS_GENERDAO.mjs"

const CIS_GENERController = {
    populate : async () => await CIS_GENERDAO.populate(),
    findAll : async (page,size) => await CIS_GENERDAO.findAll(page,size),
    findByCodeCIS: async(codecis_API) => await CIS_GENERDAO.findByCodeCIS(codecis_API)
}

export default CIS_GENERController