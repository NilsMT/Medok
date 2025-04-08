import mongoose from 'mongoose';
import { MongoServerError } from 'mongodb';
import pharmacienController from '../controller/pharmacienController.mjs';
import {expect} from 'chai';
import sinon from 'sinon';

let mongoServer;
const MongoPharmacien = mongoose.models.pharmacienCollection

sinon.stub(pharmacienController, 'populate').callsFake(async () => {
  await MongoPharmacien.create([
    { identifiant_PP: 1, code_civilite: 'M', nom_exercice: 'Doe', prenom_exercice: 'John', code_postal_coord_structure: 12345, libelle_commune_coord_structure: "Paris" },
    { identifiant_PP: 2, code_civilite: 'M', nom_exercice: 'Smith', prenom_exercice: 'Jane', code_postal_coord_structure: 67890, libelle_commune_coord_structure: "Nantes" }
  ]);
});

async function setupMongo() {
  const { MongoMemoryServer } = await import('mongodb-memory-server');
    mongoServer = await MongoMemoryServer.create();
  
    await mongoose.connect(mongoServer.getUri(), {
      useNewUrlParser: true,
      useUnifiedTopology: true
    });

    await pharmacienController.populate();
}



describe('Tests findAll', function() {

  before (async () => {
    await setupMongo();
  });

  it('Page trop basse', async () => {
    let err = null
    try {
      await pharmacienController.findAll(0, 1);
    } catch (error) {
      err = error
    }
    expect(err).to.be.instanceOf(MongoServerError);
  });

  it('Size trop basse', async () => {
    let err = null
    try {
      await pharmacienController.findAll(1, 0);
    } catch (error) {
      err = error;
    }
    expect(err).to.be.instanceOf(MongoServerError);
  });

  it('Page superieur a la derniere', async () => {
    const res = await pharmacienController.findAll(100, 1);
    expect(res.data.length).to.equal(0);  
  });

  it('Succes', async () => {
    const res = await pharmacienController.findAll(1, 2);
    expect(res.data.length).to.equal(2);
    expect(res.data[0].nom_exercice).to.equal("Doe");
    expect(res.data[1].nom_exercice).to.equal("Smith");
  });

  after(async () => {
    await mongoose.connection.db.dropDatabase();
    await mongoose.disconnect();
    await mongoServer.stop();
  });
})



describe('Tests findByID', function() {

  before(async () => {
    await setupMongo();
  });

  it('Ne trouve pas de resultat', async () => {
    const res = await pharmacienController.findByID(3);
    expect(res.data.length).to.equal(0);
  });

  it('ID négative', async () => {
    const res = await pharmacienController.findByID(-1);
    expect(res.data.length).to.equal(0);
  });

  it('Succes', async () => {
    const res = await pharmacienController.findByID(1);
    expect(res.data.length).to.equal(1);
    expect(res.data[0].nom_exercice).to.equal("Doe");
  });

  after(async () => {
    await mongoose.connection.db.dropDatabase();
    await mongoose.disconnect();
    await mongoServer.stop();
  });
});



describe('Tests findByPrenom', function() {

  before(async () => {
    await setupMongo();
  });

  it('Page trop basse', async () => {
    let err = null
    try {
      await pharmacienController.findByPrenom(" ", 0, 1);
    } catch (error) {
      err = error
    }
    expect(err).to.be.instanceOf(MongoServerError);
  });

  it('Size trop basse', async () => {
    let err = null
    try {
      await pharmacienController.findByPrenom(" ", 1, 0);
    } catch (error) {
      err = error;
    }
    expect(err).to.be.instanceOf(MongoServerError);
  });

  it('Ne trouve pas de resultat', async () => {
    const res = await pharmacienController.findByPrenom(" ", 1, 1);
    expect(res.data.length).to.equal(0);
  });

  it('Page superieur a la derniere', async () => {
    const res = await pharmacienController.findByPrenom("John", 100, 1);
    expect(res.data.length).to.equal(0);  
  });

  it('Succes', async () => {
    const res = await pharmacienController.findByPrenom("John", 1, 1);
    expect(res.data.length).to.equal(1);
    expect(res.data[0].nom_exercice).to.equal("Doe");
  });

  it('Succes (sans casse)', async () => {
    const res = await pharmacienController.findByPrenom("john", 1, 1);
    expect(res.data.length).to.equal(1);
    expect(res.data[0].nom_exercice).to.equal("Doe");
  });

  after(async () => {
    await mongoose.connection.db.dropDatabase();
    await mongoose.disconnect();
    await mongoServer.stop();
  });
});



describe('Tests findByNom', function() {

  before(async () => {
    await setupMongo();
  });

  it('Page trop basse', async () => {
    let err = null
    try {
      await pharmacienController.findByNom(" ", 0, 1);
    } catch (error) {
      err = error
    }
    expect(err).to.be.instanceOf(MongoServerError);
  });

  it('Size trop basse', async () => {
    let err = null
    try {
      await pharmacienController.findByNom(" ", 1, 0);
    } catch (error) {
      err = error;
    }
    expect(err).to.be.instanceOf(MongoServerError);
  });

  it('Ne trouve pas de resultat', async () => {
    const res = await pharmacienController.findByNom(" ", 1, 1);
    expect(res.data.length).to.equal(0);
  });

  it('Page superieur a la derniere', async () => {
    const res = await pharmacienController.findByNom("Doe", 100, 1);
    expect(res.data.length).to.equal(0);  
  });

  it('Succes', async () => {
    const res = await pharmacienController.findByNom("Doe", 1, 1);
    expect(res.data.length).to.equal(1);
    expect(res.data[0].prenom_exercice).to.equal("John");
  });

  it('Succes (sans casse)', async () => {
    const res = await pharmacienController.findByNom("doe", 1, 1);
    expect(res.data.length).to.equal(1);
    expect(res.data[0].prenom_exercice).to.equal("John");
  });

  after(async () => {
    await mongoose.connection.db.dropDatabase();
    await mongoose.disconnect();
    await mongoServer.stop();
  });

});



describe("Tests findByCodePostal", function() {

  before(async () => {
    await setupMongo();
  });

  it('Page trop basse', async () => {
    let err = null
    try {
      await pharmacienController.findByCodePostal(12345, 0, 1);
    } catch (error) {
      err = error
    }
    expect(err).to.be.instanceOf(MongoServerError);
  });

  it('Size trop basse', async () => {
    let err = null
    try {
      await pharmacienController.findByCodePostal(12345, 1, 0);
    } catch (error) {
      err = error;
    }
    expect(err).to.be.instanceOf(MongoServerError);
  });

  it('Ne trouve pas de resultat', async () => {
    const res = await pharmacienController.findByCodePostal(99999, 1, 1);
    expect(res.data.length).to.equal(0);
  });

  it('Page superieur a la derniere', async () => {
    const res = await pharmacienController.findByCodePostal(12345, 100, 1);
    expect(res.data.length).to.equal(0);  
  });

  it('Succes', async () => {
    const res = await pharmacienController.findByCodePostal(12345, 1, 1);
    expect(res.data.length).to.equal(1);
    expect(res.data[0].nom_exercice).to.equal("Doe");
  });

  after(async () => {
    await mongoose.connection.db.dropDatabase();
    await mongoose.disconnect();
    await mongoServer.stop();
  });
});

  
describe("Tests findByLibelleCommune", function() {
  before(async () => {
    await setupMongo();
    });

    it('Page trop basse', async () => {
      let err = null
      try {
        await pharmacienController.findByLibelleCommune(" ", 0, 1);
      } catch (error) {
        err = error
      }
      expect(err).to.be.instanceOf(MongoServerError);
    });

    it('Size trop basse', async () => {
      let err = null
      try {
        await pharmacienController.findByLibelleCommune(" ", 1, 0);
      } catch (error) {
        err = error;
      }
      expect(err).to.be.instanceOf(MongoServerError);
    });

    it("Ne trouve pas de resultat", async () => {
      const res = await pharmacienController.findByLibelleCommune(" ", 1, 1);
      expect(res.data.length).to.equal(0);
    });

    it('Page superieur a la derniere', async () => {
      const res = await pharmacienController.findByLibelleCommune("Paris", 100, 1);
      expect(res.data.length).to.equal(0);  
    });

    it('Succes', async () => {
      const res = await pharmacienController.findByLibelleCommune("Paris", 1, 1);
      expect(res.data.length).to.equal(1);
      expect(res.data[0].nom_exercice).to.equal("Doe");
    });

    it('Succes (sans casse)', async () => {
      const res = await pharmacienController.findByLibelleCommune("paris", 1, 1);
      expect(res.data.length).to.equal(1);
      expect(res.data[0].nom_exercice).to.equal("Doe");
    })

    after(async () => {
      await mongoose.connection.db.dropDatabase();
      await mongoose.disconnect();
      await mongoServer.stop();
    });
  })