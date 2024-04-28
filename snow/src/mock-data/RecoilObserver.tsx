import { useEffect } from "react";
import { RecoilState, useRecoilValue } from "recoil";

interface ObserverProps {
  node: RecoilState<any>;
  onChange: (value: any) => void;
}

export const RecoilObserver: React.FC<ObserverProps> = ({ node, onChange }) => {
  const value = useRecoilValue(node);
  useEffect(() => onChange(value), [onChange, value]);
  return null;
};
