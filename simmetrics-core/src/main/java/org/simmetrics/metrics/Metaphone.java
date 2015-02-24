package org.simmetrics.metrics;

/**
 * 
 * Metaphone Algorithm
 * 
 * I found this code online at the following location:
 * http://aspell.net/metaphone/metaphone-kuhn.txt and integrated the algorithm
 * into simmetrics with a few minor modifications, if some licensing dispute
 * arises I will rewrite the algorithm myself but since Aspell uses the same
 * license as simmetrics I don't forsee a problem...
 * 
 * Created by Lawrence Philips (location unknown). Metaphone presented in
 * article in "Computer Language" December 1990 issue.
 * 
 * Converted from Pick BASIC, as demonstrated in article, to C by Michael J.
 * Kuhn (Baltimore, Maryland)
 * 
 * My original intention was to replace SOUNDEX with METAPHONE in order to get
 * lists of similar sounding names that were more precise. SOUNDEX maps
 * "William" and "Williams" to the same values. METAPHONE as it turns out DOES
 * THE SAME. There are going to be problems that you need to resolve with your
 * own set of data.
 * 
 * Basically, for my problem with S's I think that if
 * 
 * IF metaphone[strlen(metaphone)] == "S" AND strlen(metaphone) >= 4 THEN
 * 
 * metaphone[strlen(metaphone)] = ""
 * 
 * You can add you own rules as required.
 * 
 * Also, Lawrence Philips suggests that for practical reasons only the first 4
 * characters of the metaphone be used. This happens to be the number of
 * characters that Soundex produces. This is indeed practical if you already
 * have reserved exactly 4 characters in your database.
 * 
 * In addition an analysis of your data may show that names are split into
 * undesirable "metaphone groups" as the number of metaphone characters
 * increases.
 * 
 * *********** BEGIN METAPHONE RULES ***********
 * 
 * Lawrence Philips' RULES follow:
 * 
 * The 16 consonant sounds: |--- ZERO represents "th" | B X S K J T F H L M N P
 * R 0 W Y
 * 
 * Exceptions:
 * 
 * Beginning of word: "ae-", "gn", "kn-", "pn-", "wr-" ----> drop first letter
 * "Aebersold", "Gnagy", "Knuth", "Pniewski", "Wright"
 * 
 * Beginning of word: "x" ----> change to "s" as in "Deng Xiaopeng"
 * 
 * Beginning of word: "wh-" ----> change to "w" as in "Whalen"
 * 
 * Transformations:
 * 
 * B ----> B unless at the end of word after "m", as in "dumb", "McComb"
 * 
 * C ----> X (sh) if "-cia-" or "-ch-" S if "-ci-", "-ce-", or "-cy-" SILENT if
 * "-sci-", "-sce-", or "-scy-" K otherwise, including in "-sch-"
 * 
 * D ----> J if in "-dge-", "-dgy-", or "-dgi-" T otherwise
 * 
 * F ----> F
 * 
 * G ----> SILENT if in "-gh-" and not at end or before a vowel in "-gn" or
 * "-gned" in "-dge-" etc., as in above rule J if before "i", or "e", or "y" if
 * not double "gg" K otherwise
 * 
 * H ----> SILENT if after vowel and no vowel follows or after "-ch-", "-sh-",
 * "-ph-", "-th-", "-gh-" H otherwise
 * 
 * J ----> J
 * 
 * K ----> SILENT if after "c" K otherwise
 * 
 * L ----> L
 * 
 * M ----> M
 * 
 * N ----> N
 * 
 * P ----> F if before "h" P otherwise
 * 
 * Q ----> K
 * 
 * R ----> R
 * 
 * S ----> X (sh) if before "h" or in "-sio-" or "-sia-" S otherwise
 * 
 * T ----> X (sh) if "-tia-" or "-tio-" 0 (th) if before "h" silent if in
 * "-tch-" T otherwise
 * 
 * V ----> F
 * 
 * W ----> SILENT if not followed by a vowel W if followed by a vowel
 * 
 * X ----> KS
 * 
 * Y ----> SILENT if not followed by a vowel Y if followed by a vowel
 * 
 * Z ----> S
 * 
 * *************************************************************
 * 
 * NOTE: This list turned out to be various issues that I passed over while
 * trying to discern this algorithm. The final outcome of these items may or may
 * not be reflected in the code.
 * 
 * There where some discrepancies between the Pick BASIC code in the original
 * article and the verbal discription of the transformations:
 * 
 * 1. CASE SYMB = "G"
 * 
 * AND ENAME[N +3] = "D" AND (N + 3) = L)) and ENAM ^ this was cut off in the
 * magazine listing |
 * 
 * I used the verbal discription in the transformation list to add the
 * appropriate code.
 * 
 * 2. H ----> SILENT if after vowel and no vowel follows H otherwise
 * 
 * This is the transformation description, however, the BASIC routine HAS code
 * do this:
 * 
 * SILENT if after "ch-", "sh-", "ph-", "th", "gh"
 * 
 * which is the correct behaviour if you look at c,s,p,t,g
 * 
 * If did not, however, have "after vowel" coded even though this was in the
 * description. I added it.
 * 
 * 3. The BASIC code appears to skip double letters except "C" yet the
 * transformation code for "G" looks at previous letter to see if we have "GG".
 * This is inconsistent.
 * 
 * I am making the assumption that "C" was a typo in the BASIC code. It should
 * have been "G".
 * 
 * 4. Transformation notation. "-..-" where .. are letters; means that the
 * letters indicated are bounded by other letters. So "-gned" means at the end
 * and "ch-" means at the beginning. I have noticed that the later is not
 * explicity stated in the verbal description but it is coded in the BASIC.
 * 
 * 5. case 'C' K otherwise, including in "-sch-" this implies that "sch" be
 * bounded by other letters. The BASIC code, however, has: N > 1 It should have
 * N > 2 for this to be correct. SCH- 123 greater than 1 means that C can be 2nd
 * letter
 * 
 * I coded it as per the verbal description and not what was in the code.
 * 
 * 6. as of 11-20-95 I am still trying to understand "H". The BASIC code seems
 * to indicate that if "-.h" is at the end it is not silent. But if it is at the
 * end there is no way a vowel could follow the "h". I am looking for examples.
 * 
 * 7. ok now I am really confused. Case "T". There is code in BASIC that says if
 * next = "H" and previous != "T" . There is no way that a double T goes through
 * the code. Double letters are dumped in the beginning.
 * 
 * MATTHEW, MATTHIES, etc
 * 
 * The first T goes through the second is skipped so the "th" is never detected.
 * 
 * Modified routine to allow "G,T" duplicates through the switch.
 * 
 * 8. case "D" -dge- is indicated in transformation -dge- or -dge is coded.
 * 
 * STEMBRIDGE should have "j" on end and not "t"
 * 
 * I am leaving the code as is, verbal must be wrong.
 * 
 * 9. Regarding duplicate letters. "C" must be allowed through as in all of the
 * McC... names.
 * 
 * The way to handle "GG and "TT" I think is to pass over the first duplicate.
 * The transformation rules would then handle duplicates of themselves by
 * looking at the PREVIOUS letter.
 * 
 * This solves the problems of "TTH" where you want the "th" sound.
 * 
 * 10. Change "CC" so that the metaphone character is "C", they way it is now
 * for McComb and such you get "MKK", which unnecessiarly eats up and extra
 * metaphone character.
 * 
 * 11. "TH" at the beginning as in Thomas. The verbal was not clear about this.
 * I think is should be "T" and not "0" so I am changing code.
 * 
 * After the first test I think that "THvowel" should be "0" and "TH(!vowel)"
 * should be "T"
 * 
 * 12. I think throwing away 1 "S" and the end would be good. Since I am doing
 * this anyway after the fact. If I do it before then names like. .. BURROUGHS &
 * BURROUGH would be the same because the GH would map to the same value in both
 * cases.
 * 
 * 13. Case "Y", Brian and Bryan give different codes Don't know how to handle
 * this yet.
 * 
 * 14. Comments on metaphone groups. Metaphone actually makes groups bigger.
 * Names like:
 * 
 * C...R... G...R... K...R... Q...R...
 * 
 * will map to "KR". Soundex would have produced for example
 * 
 * C600,C620,G600,G620,K600,K620,Q600,Q620
 * 
 * the names from these 8 groups would have been collapsed into 1.
 * 
 * Another way to look at this is for a more exact initial guess of a name
 * Soundex would give you a smaller list of posibilities. If you don't know how
 * to spell it at all however, your success at finding the right match with
 * Metaphone is much greater than with Soundex.
 * 
 * 15. After some tests decided to leave S's at the end of the Metaphone. #12
 * takes care of my problems with plurals and then S gets used to help make
 * distinct metaphone.
 * 
 * Lawrence Philips is no longer at the company indicated in the article. So I
 * was unable to verify these items.
 */
public class Metaphone {

	private static final int MAX_MLEN = 4;
	
	private String VOWELS = "AEIOU";
	private String FRONTV = "EIY";
	/* special cases for letters in FRONT of these */
	private String VARSON = "CSPTG";
	/* variable sound--those modified by adding an "h" */

	private String DOUBLE = ".";
	/* let these double letters through */
	private String excpPAIR = "AGKPW";
	/* exceptions "ae-", "gn-", "kn-", "pn-", "wr-" */
	private String nextLTR = "ENNNR";

	private String metaphone(String str) {

	int  jj, silent, hard, Lng, lastChr, metalen = MAX_MLEN;

	char curLtr, prevLtr, nextLtr, nextLtr2, nextLtr3;

	int vowelAfter, vowelBefore, frontvAfter;

	String wname;

	String metaph;
	
	String ename;

	ename = str.toUpperCase();

	if(ename.isEmpty()){
		return "";
	}

	/* if ae, gn, kn, pn, wr then drop the first letter */
	if ((chrptr = strchr(excpPAIR, ename[0])) != NULLCHAR) {
		chrptr1 = nextLTR + (chrptr - excpPAIR);
		if (*chrptr1 == ename[1])
			strcpy(ename, &ename[1]);
	}
	/* change x to s */
	if (ename[0] == 'X')
		ename[0] = 'S';
	/* get rid of the "h" in "wh" */
	if (strncmp(ename, "WH", 2) == 0)
		strcpy(&ename[1], &ename[2]);

	Lng = strlen(ename);
	lastChr = Lng - 1; /* index to last character in string makes code easier*/

	/* Remove an S from the end of the string */
	if (ename[lastChr] == 'S') {
		ename[lastChr] = '\0';
		Lng = strlen(ename);
		lastChr = Lng - 1;
	}

	for (ii = 0; ((strlen(metaph) < metalen) && (ii < Lng)); ii++) {

		curLtr = ename[ii];

		vowelBefore = false;
		prevLtr = ' ';
		if (ii > 0) {
			prevLtr = ename[ii - 1];
			if (strchr(VOWELS, prevLtr) != NULLCHAR)
				vowelBefore = true;
		}
		/* if first letter is a vowel KEEP it */
		if (ii == 0 && (strchr(VOWELS, curLtr) != NULLCHAR )) {
			strncat(metaph, &curLtr, 1);
			continue;
		}

		vowelAfter = false;
		frontvAfter = false;
		nextLtr = ' ';
		if (ii < lastChr) {
			nextLtr = ename[ii + 1];
			if (strchr(VOWELS, nextLtr) != NULLCHAR)
				vowelAfter = true;
			if (strchr(FRONTV, nextLtr) != NULLCHAR)
				frontvAfter = true;
		}
		/* skip double letters except ones in list */
		if (curLtr == nextLtr && (strchr(DOUBLE, nextLtr) == NULLCHAR ))
			continue;

		nextLtr2 = ' ';
		if (ii < (lastChr - 1))
			nextLtr2 = ename[ii + 2];

		nextLtr3 = ' ';
		if (ii < (lastChr - 2))
			nextLtr3 = ename[ii + 3];

		switch (curLtr) {

		case 'B':
			silent = false;
			if (ii == lastChr && prevLtr == 'M')
				silent = true;
			if (!silent)
				strncat(metaph, &curLtr, 1);
			break;

			/*silent -sci-,-sce-,-scy-;  sci-, etc OK*/
		case 'C':
			if (!(ii > 1 && prevLtr == 'S' && frontvAfter)) {
				if (ii > 0 && nextLtr == 'I' && nextLtr2 == 'A')
					strncat(metaph, "X", 1);
				else if (frontvAfter)
					strncat(metaph, "S", 1);
				else if (ii > 1 && prevLtr == 'S' && nextLtr == 'H')
					strncat(metaph, "K", 1);
				else if (nextLtr == 'H')
					if (ii == 0 && (strchr(VOWELS, nextLtr2) == NULLCHAR ))
						strncat(metaph, "K", 1);
					else
						strncat(metaph, "X", 1);
				else if (prevLtr == 'C')
					strncat(metaph, "C", 1);
				else
					strncat(metaph, "K", 1);
			}
			break;

		case 'D':
			if (nextLtr == 'G' && (strchr(FRONTV, nextLtr2) != NULLCHAR ))
				strncat(metaph, "J", 1);
			else
				strncat(metaph, "T", 1);
			break;

		case 'G':
			silent = false;
			/* SILENT -gh- except for -gh and no vowel after h */
			if ((ii < (lastChr - 1) && nextLtr == 'H')
					&& (strchr(VOWELS, nextLtr2) == NULLCHAR ))
				silent = true;

			if ((ii == (lastChr - 3)) && nextLtr == 'N' && nextLtr2 == 'E'
					&& nextLtr3 == 'D')
				silent = true;
			else if ((ii == (lastChr - 1)) && nextLtr == 'N')
				silent = true;

			if (prevLtr == 'D' && frontvAfter)
				silent = true;

			if (prevLtr == 'G')
				hard = true;
			else
				hard = false;

			if (!silent) {
				if (frontvAfter && (!hard))
					strncat(metaph, "J", 1);
				else
					strncat(metaph, "K", 1);
			}
			break;

		case 'H':
			silent = false;
			if (strchr(VARSON, prevLtr) != NULLCHAR)
				silent = true;

			if (vowelBefore && !vowelAfter)
				silent = true;

			if (!silent)
				strncat(metaph, &curLtr, 1);
			break;

		case 'F':
		case 'J':
		case 'L':
		case 'M':
		case 'N':
		case 'R':
			strncat(metaph, &curLtr, 1);
			break;

		case 'K':
			if (prevLtr != 'C')
				strncat(metaph, &curLtr, 1);
			break;

		case 'P':
			if (nextLtr == 'H')
				strncat(metaph, "F", 1);
			else
				strncat(metaph, "P", 1);
			break;

		case 'Q':
			strncat(metaph, "K", 1);
			break;

		case 'S':
			if (ii > 1 && nextLtr == 'I'
					&& (nextLtr2 == 'O' || nextLtr2 == 'A'))
				strncat(metaph, "X", 1);
			else if (nextLtr == 'H')
				strncat(metaph, "X", 1);
			else
				strncat(metaph, "S", 1);
			break;

		case 'T':
			if (ii > 1 && nextLtr == 'I'
					&& (nextLtr2 == 'O' || nextLtr2 == 'A'))
				strncat(metaph, "X", 1);
			else if (nextLtr == 'H') /* The=0, Tho=T, Withrow=0 */
				if (ii > 0 || (strchr(VOWELS, nextLtr2) != NULLCHAR ))
					strncat(metaph, "0", 1);
				else
					strncat(metaph, "T", 1);
			else if (!(ii < (lastChr - 2) && nextLtr == 'C' && nextLtr2 == 'H'))
				strncat(metaph, "T", 1);
			break;

		case 'V':
			strncat(metaph, "F", 1);
			break;

		case 'W':
		case 'Y':
			if (ii < lastChr && vowelAfter)
				strncat(metaph, &curLtr, 1);
			break;

		case 'X':
			strncat(metaph, "KS", 2);
			break;

		case 'Z':
			strncat(metaph, "S", 1);
			break;
		}

	}

	/*  DON'T DO THIS NOW, REMOVING "S" IN BEGINNING HAS the same effect
	 with plurals, in addition imbedded S's in the Metaphone are included
	 Lng = strlen(metaph);
	 lastChr = Lng -1;
	 if ( metaph[lastChr] == 'S' && Lng >= 3 ) metaph[lastChr] = '\0';
	 */

	return (metaph);
}

	private float metaphone_similarity(String str1, String str2) {

		String s1 = metaphone(str1);
		String s2 = metaphone(str2);

		float res = smith_waterman_gotoh_similarity(s1, s2);


		return res;

	}

}
