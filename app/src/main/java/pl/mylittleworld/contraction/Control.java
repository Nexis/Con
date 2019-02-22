package pl.mylittleworld.contraction;

import pl.mylittleworld.contraction.database.Contraction;

interface Control {

    void userWantsToDeleteThisItem(Contraction contraction);
}
