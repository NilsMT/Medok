import { CIS_HAS_SMRDAO } from "../dao/CIS_HAS_SMRDAO.mjs"

const CIS_HAS_SMRController = {
    populate : async () => await CIS_HAS_SMRDAO.populate(),
    findAll : async (page,size) => await CIS_HAS_SMRDAO.findAll(page,size),
    findByCodeCIS: async(codecis_API) => await CIS_HAS_SMRDAO.findByCodeCIS(codecis_API)
}

export default CIS_HAS_SMRController