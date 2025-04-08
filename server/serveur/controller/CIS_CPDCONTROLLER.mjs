import { CIS_CPDDAO } from "../dao/CIS_CPDDAO.mjs"

const CIS_CPDController = {
    populate : async () => await CIS_CPDDAO.populate(),
    findAll : async (page,size) => await CIS_CPDDAO.findAll(page,size),
    findByCodeCIS: async(codecis_API) => await CIS_CPDDAO.findByCodeCIS(codecis_API)
}

export default CIS_CPDController