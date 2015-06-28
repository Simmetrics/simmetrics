/*
 * SimMetrics - SimMetrics is a java library of Similarity or Distance Metrics,
 * e.g. Levenshtein Distance, that provide float based similarity measures
 * between String Data. All metrics return consistent measures rather than
 * unbounded similarity scores.
 *
 * Copyright (C) 2014 SimMetrics authors
 *
 * This file is part of SimMetrics. This program is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * SimMetrics. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * TextBrew is an modification over edit distance where we can customize the costs
 * this enables up to fuzzily match shorthands such as:
 * "clmbs blvd" and "columbus boulevard" which gives 89% match
 * @see TextBrew
 * to know more about TextBrew
 * @see <a href="http://www.ling.ohio-state.edu/~cbrew/795M/string-distance.html">TextBrew by Chris Brew</a>
 *
 */
 /*The codes mentioned above are Licensed with Apache License version 2.0*/
package org.simmetrics.metrics;

import org.simmetrics.StringMetric;

import java.util.ArrayList;
import java.util.List;


public class TextBrew implements StringMetric{

    public Costs costs = null;

    public TextBrew () {
        costs = new Costs();
    }
    private Double match=null;
    private Double insert=null;
    private Double delete=null;
    private Double substitute=null;

    /**
     * This function would set the costs for TextBrew algorithm
     * The default costs are 0.0, 0.2, 1.0, 1.0 for Match, Insert, Delete, Substitute respectively
     * @param Match
     * @param Insert
     * @param Delete
     * @param Substitute
     */
    public void setCosts(double Match, double Insert, double Delete, double Substitute)
    {
        match = Match;
        insert = Insert;
        delete = Delete;
        substitute = Substitute;
    }

    @Override
    public float compare(String a, String b) {
        if (a.isEmpty() && b.isEmpty()) {
            return 1.0f;
        }

        if (a.isEmpty() || b.isEmpty()) {
            return 0.0f;
        }
        TextBrew t = new TextBrew();
        if(match!=null&&insert!=null&&delete!=null&&substitute!=null)
        {
            t.costs.match = match;
            t.costs.insert = insert;
            t.costs.delete = delete;
            t.costs.substitute = substitute;
        }
        double len = (a.length()+ b.length())/2.0;
        int maxLen = Math.max(a.length(), b.length());

        if ((float) len / (float) maxLen <= 0.5)
            return 0.0f;

        double retScore1 = (1.0 - (t.computeSimilarity(a,b).cost) / (len));
        double retScore2 = (1.0 - (t.computeSimilarity(b,a).cost) / (len));
        double retScore = Math.max(retScore1, retScore2);
        if(retScore < 0.05)
            return 0.0f;//for all erroneous cases
        else
            return (float)retScore;
    }

    private BrewResult computeSimilarity(String left, String right) {
        if ( (null == left||left.length() == 0) && (null == right||right.length() == 0)) {
            return new BrewResult();
        }

        if ( null == left || left.length() == 0 ) {
            BrewResult result = new BrewResult();
            result.cost = right.length();
            return result;
        }

        if ( null == right || right.length() == 0 ) {
            BrewResult result = new BrewResult();
            result.cost = left.length();
            return result;
        }

        Cell[][] matrix;
        char [] leftChars  = left.toCharArray();
        char [] rightChars = right.toCharArray();

        matrix = new Cell[leftChars.length + 1][];
        for( int rowNum = 0; rowNum < matrix.length; rowNum++ ) {
            matrix[rowNum] = new Cell[rightChars.length + 1];
        }

        matrix[0][0] = new Cell();
        matrix[0][0].cost       = costs.start;
        matrix[0][0].leftChar   = '0';
        matrix[0][0].rightChar  = '0';
        matrix[0][0].hit        = false;
        matrix[0][0].tracebackX = -1;
        matrix[0][0].tracebackY = -1;
        matrix[0][0].action     = ACTION.START;
        matrix[0][0].path       = false;

        // fill in default costs
        for (int idx = 0; idx < rightChars.length; ++idx ) {
            Cell c       = matrix[0][idx+1] = new Cell();
            c.cost       = (idx+1) * costs.insert;
            c.leftChar   = '0';
            c.rightChar  = rightChars[idx];
            c.hit        = false;
            c.tracebackX = 0;
            c.tracebackY = idx;
            c.action     = ACTION.INSERT;
            c.path       = false;
        }

        for (int idx = 0; idx < leftChars.length; ++idx ) {
            Cell c       = matrix[idx+1][0] = new Cell();
            c.cost       = (idx+1) * costs.delete;
            c.leftChar   = leftChars[idx];
            c.rightChar  = '0';
            c.hit        = false;
            c.tracebackX = idx;
            c.tracebackY = 0;
            c.action     = ACTION.DELETE;
            c.path       = false;
        }

        for (Cell [] row : matrix) {
            for (int idx = 0; idx < row.length; ++idx ) {
                if (row[idx] == null) {
                    row[idx] = new Cell();
                }
            }
        }

        for ( int leftIdx = 0; leftIdx < leftChars.length; ++leftIdx ) {
            int rowIdx = leftIdx + 1;
            Cell[] row = matrix[rowIdx];
            char leftChar = leftChars[leftIdx];


            for ( int rightIdx = 0; rightIdx < rightChars.length; ++rightIdx ) {
                int colIdx = rightIdx + 1;
                Cell cell = row[colIdx];
                char rightChar = rightChars[rightIdx];
                cell.leftChar = leftChar;
                cell.rightChar = rightChar;

                boolean isHit;
                double baseCost = 0.0;
                cell.action = ACTION.MATCH;
                if ( leftChar == rightChar ) {
                    baseCost   += costs.match;
                    isHit       = true;
                    cell.action = ACTION.MATCH;
                }
                else {
                    baseCost     += costs.substitute;
                    isHit         = false;
                    cell.action   = ACTION.SUBSTITUTE;
                }

                boolean canTranspose = false;
                double transposeCost = 0.0d;
                if ( leftIdx > 1 && rightIdx > 1
                        && leftChars[leftIdx-1] == rightChars[rightIdx]
                        && leftChars[leftIdx]   == rightChars[rightIdx-1]) {
                    canTranspose = true;
                    transposeCost = matrix[rowIdx-2][colIdx-2].cost + costs.transpose;
                }

                // coming from above is a DELETE
                double upCost     = costs.delete + matrix[rowIdx - 1][colIdx    ].cost;
                // coming from the left is an INSERT
                double leftCost   = costs.insert + matrix[rowIdx    ][colIdx - 1].cost;
                // diagonal is either a MATCH or a SUBSTITUTE
                double upLeftCost = baseCost     + matrix[rowIdx - 1][colIdx - 1].cost;

                double currCost   = upLeftCost;
                cell.tracebackX = colIdx-1;
                cell.tracebackY = rowIdx-1;

                if ( leftCost < currCost ) {
                    currCost = leftCost;
                    cell.tracebackX = colIdx - 1;
                    cell.tracebackY = rowIdx;
                    cell.action = ACTION.INSERT;
                }

                if ( upCost < currCost ) {
                    currCost = upCost;
                    cell.tracebackX = colIdx;
                    cell.tracebackY = rowIdx - 1;
                    cell.action = ACTION.DELETE;
                }

                if ( canTranspose && transposeCost < currCost ) {
                    currCost = transposeCost;
                    cell.tracebackX = colIdx - 2;
                    cell.tracebackY = rowIdx - 2;
                    cell.action = ACTION.TRANSPOSE;
                }

                cell.cost = currCost;
                cell.hit = isHit;
            }
        }

        // now that the matrix is filled in, compute the traceback.
        List<Cell> traceback = new ArrayList<Cell>();
        Cell resultCell = matrix[matrix.length-1][matrix[0].length-1];
        Cell curr  = resultCell;
        curr.path  = true;
        traceback.add(0,curr);
        while (true) {
            if ( curr.tracebackX == -1 && curr.tracebackY == -1 ) {
                break;
            }
            curr = matrix[curr.tracebackY][curr.tracebackX];
            traceback.add(0, curr);
            curr.path = true;
        }

        BrewResult result = new BrewResult();
        //		result.matrix    = matrix;
        result.cost      = resultCell.cost;
        //		result.traceback = traceback;
        return result;
    }

    private enum ACTION {
        NONE, START, MATCH, INSERT, DELETE, TRANSPOSE, SUBSTITUTE
    }

    private static final class Cell {
        public int tracebackX = -1;
        public int tracebackY = -1;
        public ACTION action  = ACTION.NONE;
        public char leftChar  = '0';
        public char rightChar = '0';
        public double cost    = 0.0d;
        public boolean hit    = false;
        public boolean path   = false;

        public String toString () {
            StringBuilder sb = new StringBuilder();
            sb.append("Cell{");
            sb.append(String.format("tb.x=%2d ", tracebackX));
            sb.append(String.format("tb.y=%2d ", tracebackY));
            sb.append(String.format("%s ", action.toString().substring(0,4)));
            sb.append(String.format("l=%c ", leftChar));
            sb.append(String.format("r=%c ", rightChar));
            sb.append(String.format("c=%3.2f ", cost));
            sb.append(String.format("h=%c ", hit ? 't' : 'f'));
            sb.append(String.format("p=%c", path ? 't' : 'f'));
            sb.append("}");
            return sb.toString();
        }

    }
    private class Costs {
        /*
         * These are the defaults costs which can be used to match abbreviations or shorthands
         * For e.g. Limited can be matched with ltd
         */
        public double start = 0.0d;
        public double match = 0.0d;
        public double insert = 0.2d;
        public double delete = 1d;
        public double transpose = 2.0d;
        public double substitute = 1.0d;
    }


    private class BrewResult {
        public double cost;

    }
    @Override
    public String toString() {
        return "TextBrew";
    }
}
