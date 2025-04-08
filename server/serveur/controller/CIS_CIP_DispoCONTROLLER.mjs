import { CIS_CIP_DispoDAO } from "../dao/CIS_CIP_DispoDAO.mjs"
const CIS_CIP_DISPOController = {
    populate : async () => await CIS_CIP_DispoDAO.populate(),
    findAll : async () => await CIS_CIP_DispoDAO.findAll(),
    findByCodeCIS: async() => await CIS_CIP_DispoDAO.findByCodeCIS()
}

export default CIS_CIP_DISPOController