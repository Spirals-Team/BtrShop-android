/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.btrshop.util;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.List;

import io.btrshop.products.domain.model.BeaconObject;
import io.btrshop.products.domain.model.Position;
import io.btrshop.util.trilateration.NonLinearLeastSquaresSolver;
import io.btrshop.util.trilateration.TrilaterationFunction;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     *
     */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static Position calculPosition(List<BeaconObject> listBeacon){


        double[] distances_primi = new double[listBeacon.size()];
        for (int i = 0; i < distances_primi.length; i++) {
            distances_primi[i] = listBeacon.get(i).getDistance();
        }

        double[][] positions_primi = new double[listBeacon.size()][2];
        for (int i = 0; i < positions_primi.length; i++) {
            positions_primi[i][0] = listBeacon.get(i).getData().getMarker().getLat();
            positions_primi[i][1] = listBeacon.get(i).getData().getMarker().getLng();
        }

        /*double[][] positions = new double[][] { { 5.0, -6.0 },
                                                { 13.0, -15.0 },
                                                { 21.0, -3.0 },
                                                { 12.42, -21.2 } };
        double[] distances = new double[] { 8.06, 13.97, 23.32, 15.31 };*/

        NonLinearLeastSquaresSolver solver =
                new NonLinearLeastSquaresSolver(
                        new TrilaterationFunction(positions_primi, distances_primi),
                        new LevenbergMarquardtOptimizer());

        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        double[] calculatedPosition =  optimum.getPoint().toArray();
        return new Position(calculatedPosition[0], calculatedPosition[1]);


    }

}
