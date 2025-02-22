package com.bsobat.github.guiView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import com.bsobat.github.R;
import com.bsobat.github.databinding.TemItemBinding;
import com.bsobat.github.dto.GitHubDto;

public class ItemView implements GuiView {
    private TemItemBinding binding;
    private View rootView;


    public ItemView(LayoutInflater inflater, ViewGroup vg, Listener listener){
        binding = DataBindingUtil.inflate(inflater, R.layout.tem_item, vg, false);
        rootView = binding.getRoot();
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public Bundle getViewState() {
        return null;
    }

    public void bind(GitHubDto gitHubDto) {
        binding.setGitHubDto(gitHubDto);
    }


    public interface Listener{

    }
}

