package data.helpers


fun createRowData(
    name:String ="arriba   baked winter squash mexican style",
        description: String = "this is description",
    submittedDate: String = "2005-09-16",
    nutrition: String = "[51.5, 0.0, 13.0, 0.0, 2.0, 0.0, 4.0]",
    tags :String = "['60-minutes-or-less', 'time-to-make', 'course']"): Map<String, String> {
    return mapOf<String,String>(
        "name" to name
        , "id" to "137739",
        "minutes"  to "55",
        "contributor_id" to "47892",
        "submitted" to submittedDate,
        "tags" to tags
        , "nutrition" to nutrition,
        "n_steps" to "11",
        "description" to description,
    "steps" to "['make a choice and proceed with recipe', 'depending on size of squash , cut into half or fourths', 'remove seeds', 'for spicy squash , drizzle olive oil or melted butter over each cut squash piece', 'season with mexican seasoning mix ii', 'for sweet squash , drizzle melted honey , butter , grated piloncillo over each cut squash piece', 'season with sweet mexican spice mix', 'bake at 350 degrees , again depending on size , for 40 minutes up to an hour , until a fork can easily pierce the skin', 'be careful not to burn the squash especially if you opt to use sugar or butter', 'if you feel more comfortable , cover the squash with aluminum foil the first half hour , give or take , of baking', 'if desired , season with salt']" ,
        "ingredients" to "['winter squash', 'mexican seasoning', 'mixed spice', 'honey', 'butter', 'olive oil', 'salt']",
          "n_ingredients" to "7")
}