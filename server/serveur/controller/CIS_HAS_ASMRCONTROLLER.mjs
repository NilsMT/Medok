import { CIS_HAS_ASMRDAO } from "../dao/CIS_HAS_ASMRDAO.mjs"

const CIS_HAS_ASMRController = {
    populate : async () => await CIS_HAS_ASMRDAO.populate(),
    findAll : async (page,size) => await CIS_HAS_ASMRDAO.findAll(page,size),
    findByCodeCIS: async(codecis_API) => await CIS_HAS_ASMRDAO.findByCodeCIS(codecis_API)
}

export default CIS_HAS_ASMRController