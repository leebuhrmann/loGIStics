import React from "react";
import { render, fireEvent } from "@testing-library/react";
import SideCreationView from "./side-creation-view";
import { Component } from "lucide-react";

describe("SideCreationView", () => {
  it("should render side-creation-view", () => {
    const onCloseMock = jest.fn();

    render(<SideCreationView onClose={onCloseMock} />);
    // expect
  });

  it("should test handleSave", () => {
    const component = <SideCreationView onClose={onCloseMock} />;
    jest.spyOn();
  });
});
