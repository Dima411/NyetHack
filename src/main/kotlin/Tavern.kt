import java.io.File

// Constants
private const val TAVERN_MASTER = "Taernyl"
private const val TAVERN_NAME = "$TAVERN_MASTER's Folly"
private const val MENU_ITEM_LENGHTH = 34
private const val SYMSOL_POINT = "."

// Persons in the game
private val firstNames = setOf("Alex", "Mordoc", "Sophie", "Tariq")
private val lastName = setOf("Ironfoot", "Fernsworth", "Baggins", "Downstrider")

// Extracting a tavern menu from a file
private val menuData = File ("data/tavern-menu-data.txt")
    .readText()
    .split("\n")

// Formation of names of menu dishes
private val menuItems = List(menuData.size) {index ->
    val (_, name, _) = menuData[index].split(",")
    name
}

// Formation of the tavern`s prices
private val menuItemsPrices: Map<String, Double> = List(menuData.size) {index ->
    val (_, name, price) = menuData[index].split(",")
    name to price.toDouble()
}.toMap()

// Formation of type of menu
private val typesItemMenu: Map<String, String> = List(menuData.size) {index ->
    val (type, name, _) = menuData[index].split(",")
    name to type
}.toMap()

// Formation of the tavern's menu
private val taverMenu = List(menuData.size) { index ->
    val (type:String, name:String, price:String) = menuData[index].split(",")
    val pointLength = MENU_ITEM_LENGHTH - name.length - price.length
    val pointStr = SYMSOL_POINT.repeat(pointLength)
    val menuItem = "$name$pointStr$price"
    Pair(type, menuItem)
}

// Visit to tavern
fun visiteTavern() {
    narrate("$heroName enters $TAVERN_NAME")
    narrate("There are several items for sale:")

    // View the menu
    viewGroupedMenu()

    // The presence of gold in the characters
    val patrons: MutableSet<String> = mutableSetOf()
    val patronGold = mutableMapOf (
        TAVERN_MASTER to 86.00,
        heroName to 4.50
    )
    while (patrons.size < 5) {
        val patronName = "${firstNames.random()} ${lastName.random()}"
        patrons += patronName
        patronGold += patronName to 6.0
    }

    val readOnlypatrons = patrons.toList ()

    narrate("$heroName sees several patrons in the tavern:")
    narrate((patrons.joinToString()))

    println(patronGold)
    repeat(3) {
        placeOrder(patrons.random(), menuItems.random(), patronGold)
    }
    displayPatronBalances(patronGold)
}

// Place order in the tavern
private fun placeOrder (
    patronName: String,
    menuItemName: String,
    patronGold: MutableMap<String, Double>
) {
    val itemPrice = menuItemsPrices.getValue(menuItemName)

    narrate("$patronName speaks with $TAVERN_MASTER to place an order")
    if (itemPrice <= patronGold.getOrDefault(patronName, 0.0)) {
        val action = when (typesItemMenu[menuItemName]) {
            "shandy", "elixir" -> "pours"
            "meal" -> "serves"
            else -> "hands"
        }
        narrate("$TAVERN_MASTER $action $patronName a $menuItemName")
        narrate("$patronName pays $TAVERN_MASTER $itemPrice gold.")
        patronGold[patronName] = patronGold.getValue(patronName) - itemPrice
        patronGold[TAVERN_MASTER] = patronGold.getValue(TAVERN_MASTER) + itemPrice
    } else {
        narrate("$TAVERN_MASTER says, \"You need more coin for a $menuItemName\"")
    }
}

// Grouped menu viewing function
fun viewGroupedMenu() {
    val greetingTheVisitor = "*** Welcome to $TAVERN_NAME ***"
    narrate(greetingTheVisitor)
    val groupedMenuItemsPrice = taverMenu.groupBy { it.first }
    for ((type, items) in groupedMenuItemsPrice) {
        val typeWithChairs = "~[$type]~"
        val indentInTheMenu = (greetingTheVisitor.length - typeWithChairs.length)/2
        narrate(" ".repeat(indentInTheMenu) + typeWithChairs)
        for (item in items) {
            val menuItem = item.second.replace(Regex("\\[.*?\\]\\s*"), "")
            narrate(menuItem)
        }
    }
}

// Gold format in patrons
private fun displayPatronBalances(patronGold: Map<String, Double>) {
    narrate("$heroName intuitively knows how much money each patron has")
    patronGold.forEach {(patron, balance) ->
        narrate(("$patron has ${"%.2f".format(balance)} gold"))
    }
}