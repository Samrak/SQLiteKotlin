# README #

### What is this repository for? ###

* Quick suEasy SQLite using in Android with Kotlin Programming.
* 1.0

### How do I get set up? ###

* build.gradle

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

	dependencies {
		compile 'com.github.Samrak:SQLiteKotlin:-SNAPSHOT'
	}
  
  ### How do I create object and database table mapping? ###
 
  EntityTest.kt or Test.kt //doesn't matter.

	class EntityTest {
    var testId: Long? = null
    var testName: String? = null
    var testSurname: String? = null
    var testData: String? = null

    constructor() {}

    constructor(testId: Long, testName: String?, testSurname: String?, testData: String?) {
        this.testId = testId
        this.testName = testName
        this.testSurname = testSurname
        this.testData = testData
    	}
	}

Database table column names must match with property names.

	class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createTable3)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + createTable3)
        onCreate(db)
    }

    companion object {
        private var dataBaseHelper: SQLiteHelper? = null
        internal val DATABASE = "activate"
        internal val TABLE = "person"
        internal val DATABASE_VERSION = 1

        internal var createTable3 = ("CREATE TABLE " + "test" + " ( " + "testId" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "testName" + " VARCHAR(10), "
                + "testSurname" + " NVARCHAR(15), "
                + "testData" + " TEXT  )")

        fun getInstance(context: Context): SQLiteHelper {
            if (dataBaseHelper == null)
                return SQLiteHelper(context)
            else
                return dataBaseHelper as SQLiteHelper
        	}
    	}
	}
    
### How do I use in Project? ###

var helper = SQLiteHelper.getInstance(this) //create your helper file and put it into SQLiteAdapter
var db = SQLiteAdapter(helper)

There is only 4 element to manipulate SQLite Database.

db.insert(EntityTest(0, "samet", "öztoprak", "data"), "testId")

db.delete(EntityTest(2, "samet", "öztoprak", "data"), "testId")

db.update(EntityTest(3, "samet", "öztoprak", "data"), "testId")

val tests = db.select(EntityTest())

val tests = db.select(EntityTest(),"123") // all column with like "123" query

val tests = db.select(EntityTest(),"id","23") // column name and value

### Contribution guidelines ###

* Writing tests
* Code review
* Other guidelines

### Who do I talk to? ###

* Samet Öztoprak sametoztoprak@hotmail.com
