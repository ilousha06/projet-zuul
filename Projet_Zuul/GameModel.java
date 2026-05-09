/**
 * La classe GameModel représente le modèle du jeu (architecture MVC).
 * Elle contient toute la logique du jeu :
 * - création des salles
 * - gestion des déplacements du joueur via la classe Player
 *
 * Elle ne gère ni l'affichage ni les interactions utilisateur.
 */
public class GameModel
{
    /** Joueur du jeu (contient position et historique) */
    private final Player aPlayer;

    /**
     * Constructeur du modèle.
     * Initialise le joueur et les salles du jeu.
     */
    public GameModel()
    {
        this.aPlayer = new Player();
        createRooms();
    }

    /**
     * Crée toutes les salles du jeu et leurs connexions.
     * Chaque salle possède une description et une image associée.
     */
    private void createRooms()
    {
        Room vDortoirEast        = new Room("dans le dortoir east reserve aux soeurs", "Dortoire_East.png");
        Room vDortoirOuest       = new Room("dans le dortoir ouest plus silencieux", "Dortoire_Ouest.png");
        Room vRefectoire         = new Room("dans le refectoire commun", "refectoire.png");
        Room vCuisine            = new Room("dans la cuisine ou les repas sont prepares", "Cuisine.png");
        Room vInfirmerie         = new Room("dans l infirmerie aux lumieres froides", "Infirmerie.png");
        Room vHallCeremonies     = new Room("dans le hall des ceremonies", "Hall.png");
        Room vCourIntern         = new Room("dans la cour interieure entouree de murs", "Cour.png");
        Room vJardinCentral      = new Room("dans le jardin central sombre", "Jardin.png");
        Room vPuitsAncien        = new Room("pres du puits ancien en pierre", "Puits.png");
        Room vSalleCachee        = new Room("dans une salle cachee sous le puits", "font_puit.png");
        Room vSerreCultivee      = new Room("dans la serre cultivee abandonnee", "serre.png");
        Room vLabyrintheVeg      = new Room("dans le labyrinthe vegetal inquietant", "Labyrinthe.png");
        Room vAutelExtern        = new Room("pres de l'autel exterieur en pierre", "autel.png"); // Pas encore d'image
        Room vCabaneJardin       = new Room("dans la cabane du jardinier", "Cabane.png");
        Room vChapPrinci         = new Room("dans la chapelle principale silencieuse", "Chapelle.png");
        Room vBiblioSacree       = new Room("dans la bibliotheque sacree", "Bibliotheque.png");
        Room vArchiInterd        = new Room("dans les archives interdites", "Archives.png");
        Room vCloitreIntern      = new Room("dans le cloitre interieur", "cloitre.png");
        Room vSalleSerments      = new Room("dans la salle des serments", "serment.png");
        Room vBureauMatriarche   = new Room("dans le bureau de la matriarche", "bureau.png");// Pas encore d'image
        Room vAntichambreSacree  = new Room("dans l antichambre sacree", "antichambre.png");// Pas encore d'image
        Room vSalleRituels       = new Room("dans la salle des rituels", "rituels.png");// Pas encore d'image
        Room vSanctuaireIntern   = new Room("dans le sanctuaire interieur", "sanctuaire.png");// Pas encore d'image
        Room vCrypteAncienne     = new Room("dans la crypte ancienne", "crypte.png");// Pas encore d'image
        Room vChambreReliques    = new Room("dans la chambre des reliques", "reliques.png");// Pas encore d'image
        Room vEscalierCave       = new Room("dans l escalier menant a la cave", "escalier.png");// Pas encore d'image
        Room vCavePrincipale     = new Room("dans la cave principale", "cave.png");// Pas encore d'image
        Room vPorteScellee       = new Room("devant la porte scellee", "porte.png");// Pas encore d'image
        Room vSortieExtern       = new Room("devant la sortie exterieure", "sortie.png");// Pas encore d'image

        // Connexions
        vCourIntern.setExit("north", vDortoirOuest);
        vCourIntern.setExit("south", vJardinCentral);
        vCourIntern.setExit("east",  vHallCeremonies);
        vCourIntern.setExit("west",  vRefectoire);

        vDortoirEast.setExit("west", vDortoirOuest);
        vDortoirOuest.setExit("south", vCourIntern);
        vDortoirOuest.setExit("west", vInfirmerie);
        vDortoirOuest.setExit("east", vDortoirEast);

        vInfirmerie.setExit("east", vDortoirOuest);

        vRefectoire.setExit("east", vCourIntern);
        vRefectoire.setExit("north", vCuisine);
        vCuisine.setExit("south", vRefectoire);

        vJardinCentral.setExit("north", vCourIntern);
        vJardinCentral.setExit("south", vPuitsAncien);
        vJardinCentral.setExit("east", vAutelExtern);
        vJardinCentral.setExit("west", vLabyrintheVeg);

        vPuitsAncien.setExit("north", vJardinCentral);
        vPuitsAncien.setExit("down", vSalleCachee);
        // vSalleCachee.setExit("up", vPuitsAncien);

        vLabyrintheVeg.setExit("east", vJardinCentral);
        vLabyrintheVeg.setExit("west", vCabaneJardin);

        vCabaneJardin.setExit("east", vLabyrintheVeg);
        vAutelExtern.setExit("west", vJardinCentral);
        vAutelExtern.setExit("south", vSerreCultivee);
        vSerreCultivee.setExit("north", vAutelExtern);

        vHallCeremonies.setExit("west", vCourIntern);
        vHallCeremonies.setExit("north", vSalleSerments);
        vHallCeremonies.setExit("south", vCloitreIntern);

        vSalleSerments.setExit("south", vHallCeremonies);
        vSalleSerments.setExit("east", vAntichambreSacree);

        vAntichambreSacree.setExit("west", vSalleSerments);
        vAntichambreSacree.setExit("south", vBureauMatriarche);

        vBureauMatriarche.setExit("north", vAntichambreSacree);

        vCloitreIntern.setExit("north", vHallCeremonies);
        vCloitreIntern.setExit("south", vSanctuaireIntern);
        vCloitreIntern.setExit("east", vBiblioSacree);
        vCloitreIntern.setExit("west", vChapPrinci);

        vChapPrinci.setExit("east", vCloitreIntern);
        vBiblioSacree.setExit("west", vCloitreIntern);
        vBiblioSacree.setExit("north", vArchiInterd);
        vArchiInterd.setExit("south", vBiblioSacree);

        vSanctuaireIntern.setExit("north", vCloitreIntern);
        vSanctuaireIntern.setExit("south", vCrypteAncienne);
        vSanctuaireIntern.setExit("east", vSalleRituels);

        vSalleRituels.setExit("west", vSanctuaireIntern);

        vCrypteAncienne.setExit("north", vSanctuaireIntern);
        vCrypteAncienne.setExit("east", vChambreReliques);

        vChambreReliques.setExit("west", vCrypteAncienne);

        vCrypteAncienne.setExit("east", vEscalierCave);
        vEscalierCave.setExit("north", vCrypteAncienne);

        vEscalierCave.setExit("down", vCavePrincipale);
        vCavePrincipale.setExit("up", vEscalierCave);

        vCavePrincipale.setExit("south", vPorteScellee);
        vPorteScellee.setExit("north", vCavePrincipale);

        vPorteScellee.setExit("south", vSortieExtern);
        vSortieExtern.setExit("north", vPorteScellee);

        // ITEMS
        Item journal = new Item("journal","un journal intime d'une ancienne novice", 1);
        Item pain = new Item("pain", "un pain au goût étrange", 1);

        Item livre = new Item("livre interdit","un livre interdit sur les rituels", 2);
        Item cleRouille = new Item("clé rouillée", "une clé rouillée sans doute pour un coffre?" ,1);
        Item document = new Item("document","un document secret sur la communauté", 1);

        Item bougie = new Item("bougie noire","une bougie noire à mettre dans la lanterne", 1);
        Item symbole = new Item("symbole","un symbole gravé étrange dans une feuille", 1);
        Item amulette = new Item("amulette","une amulette ancienne", 2);

        Item cleFinale = new Item("clé ancienne","une clé ancienne massive", 2);
        Item outil = new Item("outil","un outil cassé", 2);

        Item cleRouge = new Item("clé rouge","une clé rouge étrange", 1);

        Item papier1 = new Item("morceau1","un morceau de papier avec le chiffre 3", 1);
        Item papier2 = new Item("morceau2","un morceau de papier avec le chiffre 7", 1);
        Item papier3 = new Item("morceau3","un morceau de papier avec le chiffre 1", 1);
        Item papier4 = new Item("morceau4","un morceau de papier avec le chiffre 9", 1);

        Item seringue = new Item("seringue","une seringue contenant un liquide inconnu", 1);
        Item bible = new Item("bible","une vieille bible usée", 2);
        Item croix = new Item("croix","une croix en bois sombre", 1);

        // PLACEMENT
        vDortoirEast.addItem(journal);
        vRefectoire.addItem(pain);

        vBiblioSacree.addItem(livre);
        vSalleCachee.addItem(cleRouille);
        vBureauMatriarche.addItem(document);

        vSalleSerments.addItem(bougie);
        vSanctuaireIntern.addItem(symbole);
        vCrypteAncienne.addItem(amulette);

        vCavePrincipale.addItem(cleFinale);
        vCabaneJardin.addItem(outil);

        vSalleRituels.addItem(cleRouge);

        vDortoirOuest.addItem(papier1);
        vCuisine.addItem(papier2);
        vJardinCentral.addItem(papier3);
        vChambreReliques.addItem(papier4);

        vInfirmerie.addItem(seringue);
        vChapPrinci.addItem(bible);
        vCloitreIntern.addItem(croix);

        // ITEMS spéciaux
        Item hostie = new Item("hostie", "une hostie sacrée mystérieuse", 1);
        Item elixir = new Item("elixir", "un élixir noir puissant", 2);
        Item herbes = new Item("herbes", "des herbes médicinales", 1);
        Item painBeni = new Item("painbeni", "un pain béni", 1);
        Item encens = new Item("encens", "un encens mystique", 1);
        Item poison = new Item("poison", "un liquide toxique dangereux", 1);
        Item relique = new Item("relique", "une relique sacrée ancienne", 2);

        // PLACEMENT
        vChapPrinci.addItem(hostie);
        vSalleRituels.addItem(elixir);
        vInfirmerie.addItem(herbes);
        vRefectoire.addItem(painBeni);
        vSanctuaireIntern.addItem(encens);
        vCavePrincipale.addItem(poison);
        vCrypteAncienne.addItem(relique);

        // DEPART
        this.aPlayer.setCurrentRoom(vDortoirEast);
    }

    /**
     * Retourne la pièce actuelle du joueur.
     */
    public Room getCurrentRoom()
    {
        return this.aPlayer.getCurrentRoom();
    }

    /**
     * Déplace le joueur dans une direction donnée.
     */
    public void goRoom(String direction)
    {
        Room next = this.aPlayer.getCurrentRoom().getExit(direction);

        if(next != null) {
            this.aPlayer.goRoom(next);
        }
    }

    /**
     * Permet de revenir à la salle précédente.
     */
    public boolean goBack()
    {
        return this.aPlayer.goBack();
    }

    /**
     * Donne accès au joueur
     */
    public Player getPlayer()
    {
        return this.aPlayer;
    }
}