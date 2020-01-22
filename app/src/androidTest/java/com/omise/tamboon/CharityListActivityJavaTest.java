package com.omise.tamboon;

import android.view.View;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import androidx.test.rule.ActivityTestRule;

import com.omise.tamboon.service.repository.TamboonRepository;
import com.omise.tamboon.ui.common.TamboonViewModel;
import com.omise.tamboon.ui.listing.CharityAdapter;
import com.omise.tamboon.ui.listing.CharityListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;


@MediumTest
@RunWith(AndroidJUnit4.class)
public class CharityListActivityJavaTest {

    @Rule
    public ActivityTestRule<CharityListActivity> rule = new ActivityTestRule<>(CharityListActivity.class);
    @Mock
    private TamboonViewModel viewModel;
    @Mock
    private CharityAdapter charityAdapter;

    @Test
    public void ensureFrameLayoutIsPresent() throws Exception {

        CharityListActivity activity = rule.getActivity();
        View recyclerView = activity.findViewById(R.id.charityList);
        assertThat(recyclerView, instanceOf(RecyclerView.class));
        assertEquals(activity.findViewById(R.id.progressBar).getVisibility(), View.VISIBLE);
        recyclerView = (RecyclerView) recyclerView;

        viewModel = ViewModelProviders.of(activity, new TamboonViewModel.Factory(TamboonRepository.getInstance().getTamboonService()))
                .get(TamboonViewModel.class);
        viewModel.getCharityList();
        assertEquals(activity.findViewById(R.id.progressBar).getVisibility(), View.VISIBLE);
        charityAdapter = (CharityAdapter) ((RecyclerView) recyclerView).getAdapter();

        int count = charityAdapter.getItemCount();
        assertThat(count, greaterThan(0));

    }
}
