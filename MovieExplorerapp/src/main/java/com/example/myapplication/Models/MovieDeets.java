package com.example.myapplication.Models;

public class MovieDeets {
    // init the movie details based on how the json response is formatted
    private int id;
    private String title;
    private String release_date;
    private String poster_path;
    private String overview;


    public int getId() {
        return id;
    }

    private Genres[] genres;

    private ProductionCompanies[] production_companies;

    private ProductionCountries[] production_countries;

    private int runtime;

    private SpokenLanguages[] spoken_languages;

    private float vote_average;
    private int vote_count;

    private Cast[] cast;
    private float popularity;
    private Crew[] crew;

    public String getTitle() {
        return title;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public Genres[] getGenres() {
        return genres;
    }

    public ProductionCompanies[] getProduction_companies() {
        return production_companies;
    }

    public ProductionCountries[] getProduction_countries() {
        return production_countries;
    }

    public int getRuntime() {
        return runtime;
    }

    public SpokenLanguages[] getSpoken_languages() {
        return spoken_languages;
    }

    public float getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Cast[] getCast() {
        return cast;
    }

    public Crew[] getCrew() {
        return crew;
    }
    public  class Genres {
        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
    public static class ProductionCompanies {
        private int id;
        private  String name;
        private String origin_country;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getOrigin_country() {
            return origin_country;
        }
    }
    public class ProductionCountries {
        private String iso_3166_1;
        private String name;

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public String getName() {
            return name;
        }
    }
    public  class SpokenLanguages {
        private String iso_639_1;
        private  String name;

        public String getIso_639_1() {
            return iso_639_1;
        }

        public  String getName() {
            return name;
        }
    }
    public class Cast
    {
        private String name;
        private String character;
        private String profile_path;

        public String getName() {
            return name;
        }

        public String getCharacter() {
            return character;
        }

        public String getProfile_path() {
            return profile_path;
        }
    }
    public class Crew
    {
        private String name;
        private String job;
        private String profile_path;

        public String getName() {
            return name;
        }

        public String getJob() {
            return job;
        }

        public String getProfile_path() {
            return profile_path;
        }
    }

}
