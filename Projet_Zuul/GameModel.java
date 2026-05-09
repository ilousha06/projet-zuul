/**
 * La classe GameModel represente le modele du jeu (architecture MVC).
 * Elle contient toute la logique du jeu :
 * - creation des salles
 * - gestion des deplacements du joueur via la classe Player
 *
 * Elle ne gere ni l affichage ni les interactions utilisateur.
 */
public class GameModel
{
    /** Joueur du jeu */
    private final Player aPlayer;

    /** Le beamer (relique occulte) */
    private Beamer beamer;

    /** References aux salles pour pouvoir les modifier apres creation */
    private Room vSalleCachee;
    private Room vCrypteAncienne;
    private Room vSanctuaireIntern;
    private Room vArchiInterd;

    /**
     * Constructeur du modele.
     * Initialise le joueur et les salles du jeu.
     */
    public GameModel()
    {
        this.aPlayer = new Player();
        createRooms();
    }

    /**
     * Cree toutes les salles du jeu et leurs connexions.
     */
    private void createRooms()
    {
        Room vDortoirEast       = new Room("dans le dortoir east reserve aux soeurs",   "Dortoire_East.png");
        Room vDortoirOuest      = new Room("dans le dortoir ouest plus silencieux",      "Dortoire_Ouest.png");
        Room vRefectoire        = new Room("dans le refectoire commun",                  "refectoire.png");
        Room vCuisine           = new Room("dans la cuisine ou les repas sont prepares", "Cuisine.png");
        Room vInfirmerie        = new Room("dans l infirmerie aux lumieres froides",     "Infirmerie.png");
        Room vHallCeremonies    = new Room("dans le hall des ceremonies",                "Hall.png");
        Room vCourIntern        = new Room("dans la cour interieure entouree de murs",   "Cour.png");
        Room vJardinCentral     = new Room("dans le jardin central sombre",              "Jardin.png");
        Room vPuitsAncien       = new Room("pres du puits ancien en pierre",             "Puits.png");
        Room vSerreCultivee     = new Room("dans la serre cultivee abandonnee",          "serre.png");
        Room vLabyrintheVeg     = new Room("dans le labyrinthe vegetal inquietant",      "Labyrinthe.png");
        Room vAutelExtern       = new Room("pres de l autel exterieur en pierre",        "autel.png");
        Room vCabaneJardin      = new Room("dans la cabane du jardinier",                "Cabane.png");
        Room vChapPrinci        = new Room("dans la chapelle principale silencieuse",    "Chapelle.png");
        Room vBiblioSacree      = new Room("dans la bibliotheque sacree",                "Bibliotheque.png");
        Room vCloitreIntern     = new Room("dans le cloitre interieur",                  "cloitre.png");
        Room vSalleSerments     = new Room("dans la salle des serments",                 "serment.png");
        Room vBureauMatriarche  = new Room("dans le bureau de la matriarche",            "bureau.png");
        Room vAntichambreSacree = new Room("dans l antichambre sacree",                  "antichambre.png");
        Room vSalleRituels      = new Room("dans la salle des rituels",                  "rituels.png");
        Room vChambreReliques   = new Room("dans la chambre des reliques",               "reliques.png");
        Room vEscalierCave      = new Room("dans l escalier menant a la cave",           "escalier.png");
        Room vCavePrincipale    = new Room("dans la cave principale",                    "cave.png");
        Room vPorteScellee      = new Room("devant la porte scellee",                    "porte.png");
        Room vSortieExtern      = new Room("devant la sortie exterieure",                "sortie.png");

        // Salles gardees en references pour pouvoir les modifier
        this.vSalleCachee      = new Room("dans une salle cachee sous le puits",  "font_puit.png");
        this.vCrypteAncienne   = new Room("dans la crypte ancienne",              "crypte.png");
        this.vSanctuaireIntern = new Room("dans le sanctuaire interieur",         "sanctuaire.png");
        this.vArchiInterd      = new Room("dans les archives interdites",          "Archives.png");

        // Connexions
        vCourIntern.setExit("north", vDortoirOuest);
        vCourIntern.setExit("south", vJardinCentral);
        vCourIntern.setExit("east",  vHallCeremonies);
        vCourIntern.setExit("west",  vRefectoire);

        vDortoirEast.setExit("west",   vDortoirOuest);
        vDortoirOuest.setExit("south", vCourIntern);
        vDortoirOuest.setExit("west",  vInfirmerie);
        vDortoirOuest.setExit("east",  vDortoirEast);

        vInfirmerie.setExit("east", vDortoirOuest);

        vRefectoire.setExit("east",  vCourIntern);
        vRefectoire.setExit("north", vCuisine);
        vCuisine.setExit("south", vRefectoire);

        vJardinCentral.setExit("north", vCourIntern);
        vJardinCentral.setExit("south", vPuitsAncien);
        vJardinCentral.setExit("east",  vAutelExtern);
        vJardinCentral.setExit("west",  vLabyrintheVeg);

        // Trap door : descente uniquement, pas de remontee
        vPuitsAncien.setExit("north", vJardinCentral);
        vPuitsAncien.setExit("down",  vSalleCachee);

        vLabyrintheVeg.setExit("east", vJardinCentral);
        vLabyrintheVeg.setExit("west", vCabaneJardin);
        vCabaneJardin.setExit("east",  vLabyrintheVeg);

        vAutelExtern.setExit("west",   vJardinCentral);
        vAutelExtern.setExit("south",  vSerreCultivee);
        vSerreCultivee.setExit("north", vAutelExtern);

        vHallCeremonies.setExit("west",  vCourIntern);
        vHallCeremonies.setExit("north", vSalleSerments);
        vHallCeremonies.setExit("south", vCloitreIntern);

        vSalleSerments.setExit("south", vHallCeremonies);
        vSalleSerments.setExit("east",  vAntichambreSacree);

        vAntichambreSacree.setExit("west",  vSalleSerments);
        vAntichambreSacree.setExit("south", vBureauMatriarche);
        vBureauMatriarche.setExit("north",  vAntichambreSacree);

        // Cloitre : sortie south vers sanctuaire verrouillee (ajoutee apres avec clematri)
        vCloitreIntern.setExit("north", vHallCeremonies);
        vCloitreIntern.setExit("east",  vBiblioSacree);
        vCloitreIntern.setExit("west",  vChapPrinci);
        vChapPrinci.setExit("east", vCloitreIntern);

        // Bibliotheque : sortie north vers archives verrouillee (ajoutee apres avec livre + clearchive)
        vBiblioSacree.setExit("west", vCloitreIntern);

        vSanctuaireIntern.setExit("north", vCloitreIntern);
        vSanctuaireIntern.setExit("south", vCrypteAncienne);
        vSanctuaireIntern.setExit("east",  vSalleRituels);
        vSalleRituels.setExit("west", vSanctuaireIntern);

        vCrypteAncienne.setExit("north", vSanctuaireIntern);
        vCrypteAncienne.setExit("east",  vChambreReliques);
        vCrypteAncienne.setExit("south", vEscalierCave);
        vChambreReliques.setExit("west", vCrypteAncienne);

        // Salle cachee vers crypte verrouillee (ouverte apres pioche)
        vSalleCachee.setExit("east", vCrypteAncienne);

        vArchiInterd.setExit("south", vBiblioSacree);

        // Escalier vers cave verrouillee (ouverte apres code + clerouge + encens)
        vEscalierCave.setExit("north", vCrypteAncienne);

        vCavePrincipale.setExit("up",    vEscalierCave);
        vCavePrincipale.setExit("south", vPorteScellee);

        // Porte scellee verrouillee (ouverte apres clefinale)
        vPorteScellee.setExit("north", vCavePrincipale);

        vSortieExtern.setExit("north", vPorteScellee);

        // Items classiques
        Item journal    = new Item("journal",    "un journal d une ancienne novice - elle parle d une salle interdite sous le puits", 1);
        Item pain       = new Item("pain",       "un pain au gout etrange",                                                           1);
        Item livre      = new Item("livre",      "un livre interdit sur les rituels - sert d autorisation pour les archives",         2);
        Item cle        = new Item("cle",        "une cle rouilee - pour un coffre quelque part",                                     1);
        Item document   = new Item("document",   "un document secret avec des noms de femmes disparues - la preuve que vous cherchez", 1);
        Item bougie     = new Item("bougie",     "une bougie noire - la flamme revele ce que les yeux refusent de voir",              1);
        Item symbole    = new Item("symbole",    "un symbole grave etrange - vous reconnaissez des visages graves dedans",            1);
        Item amulette   = new Item("amulette",   "une amulette ancienne qui pulse d une energie etrange",                             2);
        Item cleFinale  = new Item("clefinale",  "une cle ancienne massive - pour la porte scellee",                                  2);
        Item outil      = new Item("outil",      "un outil casse - inutilisable",                                                     2);
        Item cleRouge   = new Item("clerouge",   "une cle rouge etrange - pour la serrure de l escalier",                            1);
        Item papier1    = new Item("morceau1",   "un morceau de papier avec le chiffre 3",                                            1);
        Item papier2    = new Item("morceau2",   "un morceau de papier avec le chiffre 7",                                            1);
        Item papier3    = new Item("morceau3",   "un morceau de papier avec le chiffre 1",                                            1);
        Item papier4    = new Item("morceau4",   "un morceau de papier avec le chiffre 9",                                            1);
        Item seringue   = new Item("seringue",   "une seringue contenant un liquide inconnu - quelque chose ne va pas ici",           1);
        Item bible      = new Item("bible",      "une vieille bible usee",                                                            2);
        Item croix      = new Item("croix",      "une croix en bois sombre",                                                          1);
        Item pioche     = new Item("pioche",     "une pioche solide qui a servi recemment - pour casser les murs",                    3);
        Item clematri   = new Item("clematri",   "la cle doree de la Matriarche - ouvre le passage vers le sanctuaire",              1);
        Item clearchive = new Item("clearchive", "une petite cle pour les archives interdites",                                       1);

        // Placement des items classiques
        vDortoirEast.addItem(journal);
        vRefectoire.addItem(pain);
        vBiblioSacree.addItem(livre);
        vSalleCachee.addItem(cle);
        vSalleCachee.addItem(clearchive);
        vBureauMatriarche.addItem(document);
        vBureauMatriarche.addItem(clematri);
        vSalleSerments.addItem(bougie);
        vSanctuaireIntern.addItem(symbole);
        vCrypteAncienne.addItem(amulette);
        vCavePrincipale.addItem(cleFinale);
        vCabaneJardin.addItem(outil);
        vCabaneJardin.addItem(pioche);
        vSalleRituels.addItem(cleRouge);
        vDortoirOuest.addItem(papier1);
        vCuisine.addItem(papier2);
        vJardinCentral.addItem(papier3);
        vChambreReliques.addItem(papier4);
        vInfirmerie.addItem(seringue);
        vChapPrinci.addItem(bible);
        vCloitreIntern.addItem(croix);

        // Items speciaux
        Item hostie   = new Item("hostie",   "une hostie sacree mysterieuse - double votre force",         1);
        Item elixir   = new Item("elixir",   "un elixir noir puissant - augmente votre capacite",          2);
        Item herbes   = new Item("herbes",   "des herbes medicinales - apaisent l esprit",                  1);
        Item painBeni = new Item("painbeni", "un pain beni - apporte du reconfort",                         1);
        Item encens   = new Item("encens",   "un encens mystique - active les mecanismes anciens",          1);
        Item poison   = new Item("poison",   "un liquide toxique dangereux - affaiblit celui qui le boit",  1);
        Item relique  = new Item("relique",  "une relique sacree ancienne - donne une force immense",       2);
        Item recharge = new Item("recharge", "un cristal qui pulse de la meme energie que le beamer",       1);

        // Placement des items speciaux
        vChapPrinci.addItem(hostie);
        vSalleRituels.addItem(elixir);
        vInfirmerie.addItem(herbes);
        vRefectoire.addItem(painBeni);
        vSanctuaireIntern.addItem(encens);
        vCavePrincipale.addItem(poison);
        vCrypteAncienne.addItem(relique);
        vArchiInterd.addItem(recharge);

        // Beamer dans la chambre des reliques
        this.beamer = new Beamer();
        vChambreReliques.addItem(this.beamer);

        // Depart du joueur
        this.aPlayer.setCurrentRoom(vDortoirEast);
    }

    /**
     * Ouvre le passage entre la salle cachee et la crypte ancienne.
     * Appele apres que le joueur a utilise la pioche.
     */
    public void ouvrirPassageCrypte()
    {
        this.vSalleCachee.setExit("north", this.vCrypteAncienne);
        this.vCrypteAncienne.setExit("south", this.vSalleCachee);
    }

    /**
     * Ouvre le passage entre le cloitre et le sanctuaire.
     * Appele apres que le joueur utilise la cle de la matriarche.
     */
    public void ouvrirPassageSanctuaire()
    {
        this.vCloitreIntern.setExit("south", this.vSanctuaireIntern);
    }

    /**
     * Ouvre le passage entre la bibliotheque et les archives.
     * Appele apres que le joueur utilise livre + clearchive.
     */
    public void ouvrirPassageArchives()
    {
        this.vBiblioSacree.setExit("north", this.vArchiInterd);
    }

    /**
     * Ouvre le passage entre l escalier et la cave.
     * Appele apres verification du code complet.
     */
    public void ouvrirPassageCave()
    {
        this.vEscalierCave.setExit("down", this.vCavePrincipale);
    }

    /**
     * Ouvre la porte scellee vers la sortie.
     * Appele apres que le joueur utilise la cle finale.
     */
    public void ouvrirPorteScellee()
    {
        this.vPorteScellee.setExit("south", this.vSortieExtern);
    }

    /** References aux salles pour les portes */
    private Room vCloitreIntern;
    private Room vBiblioSacree;
    private Room vEscalierCave;
    private Room vCavePrincipale;
    private Room vPorteScellee;
    private Room vSortieExtern;

    /**
     * Retourne la piece actuelle du joueur.
     */
    public Room getCurrentRoom()
    {
        return this.aPlayer.getCurrentRoom();
    }

    /**
     * Deplace le joueur dans une direction donnee.
     */
    public void goRoom(String direction)
    {
        Room next = this.aPlayer.getCurrentRoom().getExit(direction);
        if (next != null) {
            this.aPlayer.goRoom(next);
        }
    }

    /**
     * Permet de revenir a la salle precedente.
     */
    public void goBack()
    {
        this.aPlayer.goBack();
    }

    /**
     * Donne acces au joueur.
     */
    public Player getPlayer()
    {
        return this.aPlayer;
    }

    /**
     * Retourne le beamer du jeu.
     */
    public Beamer getBeamer()
    {
        return this.beamer;
    }
}