import { CIS_BDPMDAO } from "../dao/CIS_BDPMDAO.mjs";

const CIS_BDPMController = {
    populate : async () => await CIS_BDPMDAO.populate(),
    findAll : async (page, size) => await CIS_BDPMDAO.findAll(page, size),
    findByCodeCIS: async(codecis_API) => await CIS_BDPMDAO.findByCodeCIS(codecis_API)
}

export default CIS_BDPMController